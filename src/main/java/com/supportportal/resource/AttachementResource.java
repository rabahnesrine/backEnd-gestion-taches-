package com.supportportal.resource;

import com.supportportal.domain.Attachement;
import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.Sprint;
import com.supportportal.domain.User;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.resource.util.MediaTypeUtils;
import com.supportportal.service.AttachementService;
import com.supportportal.service.TaskService;
import com.supportportal.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.supportportal.constant.FileConstant.FILE_PATH;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path={"/attachement"})

public class AttachementResource {
    private static final String ATTACHEMENT_DELETED_SUCCESSFULLY = "attachemnt deleted successfully";
    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

    private AttachementService attachementService;
    @SuppressWarnings("unused")
    private TaskService taskService;
    List<String> files = new ArrayList<String>();
    private ServletContext servletContext;
   // private static final String FILE_PATH = "C:\\Users\\USER\\supportportal\\Attachement\\";

    @Autowired
    public AttachementResource(AttachementService attachementService, TaskService taskService, List<String> files, ServletContext servletContext) {
        this.attachementService = attachementService;
        this.taskService = taskService;
        this.files = files;
        this.servletContext = servletContext;
    }





    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file ) {
        LOGGER.info("||||||||||||"+file.getOriginalFilename());

        String message = "";
        try {
            attachementService.store(file);

            message = "upload de fichier  " + file.getOriginalFilename() + "avec success !";
LOGGER.info(message);
            LOGGER.info("||||||||||||"+file.getOriginalFilename());

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "erreur upload  " + file.getOriginalFilename() + "!";

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    /** testing to upload file

    @PostMapping("/uploadFile")
    public ResponseEntity<Attachement> uploadAtt(@RequestParam("idAttachement") Long idAttachement,
                                                   @RequestParam( value = "file")MultipartFile file )  {
        Attachement Att=attachementService.uploadAtt(idAttachement,file);
        return new ResponseEntity<>(Attachement,OK);
    }
   /* @GetMapping(path="/image/{username}/{fileName}",produces =IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username,
                                  @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER+username+FORWARD_SLASH+fileName));
        //user.home + /supportPortal/user/+ness+/+ness.jpg
    }*/

    /****** */




    @GetMapping("/getallfiles")
    public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files.stream()
                .map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(AttachementResource.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = attachementService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

 /*   @PostMapping("/add")
    public Attachement saveAttachement(@RequestBody Attachement attachement) {
        attachement.setDateCreation(new Date());
        int length = attachement.getFile().length();
        String path = attachement.getFile().substring(12, length);
        attachement.setFile(path);
        return attachementService.saveAttachement(attachement);
    }*/



    @PostMapping("/add")
    public ResponseEntity<Attachement> addAttachement(@RequestBody Attachement attachement) {

        Attachement addedAttachement=attachementService.saveAtt(attachement.getIdAttachement(),
               attachement.getFile(),attachement.getDateCreation(),attachement.getTaskAtt() );
        return new ResponseEntity<>(addedAttachement,OK);

    }



    @GetMapping("/all")
    public List<Attachement> findAllAttachement() {
        return attachementService.findAllAttachement();
    }




    @GetMapping("/task/{idTask}")
    public List<Attachement> findAttachementByTaskAtt(@PathVariable("idTask") Long idTask) {
        return attachementService.findAttachementByIdTask(idTask);
    }

    /*@DeleteMapping("/delete/{idAttachement}")
    public void deleteAttachement(@PathVariable("idAttachement") Long idAttachement) {
        attachementService.deleteAttachementById(idAttachement);
    }*/


    @DeleteMapping("/delete/{idAttachement}")
    //@PreAuthorize("hasAnyAuthority('task:delete')")

    public ResponseEntity<HttpResponse> deleteAtt(@PathVariable("idAttachement") Long idAttachement) {
        attachementService.deleteAtt(idAttachement);
        return  response(NO_CONTENT,  ATTACHEMENT_DELETED_SUCCESSFULLY);

    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<InputStreamResource> handleFileDownload(@PathVariable(value = "fileName") String fileName,
                                                                  HttpServletResponse response) throws IOException {
        // Resource resource = documentService.loadFileAsResource(fileName);
         Resource resource = attachementService.loadFile(fileName);
//LOGGER.info(resource.getFile().toString());

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        // Try to determine file's content type

        File file = new File(FILE_PATH + "/Attachement/" + fileName);
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
        System.out.println("+------------------------------------+");
        System.out.println(fileName);
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(inputStreamResource);

    }
}

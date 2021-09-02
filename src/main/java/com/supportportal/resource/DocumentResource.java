package com.supportportal.resource;

import com.supportportal.domain.Attachement;
import com.supportportal.domain.Document;
import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.User;
import com.supportportal.repository.DocumentRepository;
import org.springframework.http.HttpHeaders;
import com.supportportal.repository.TaskRepository;
import com.supportportal.resource.util.MediaTypeUtils;
import com.supportportal.service.*;
import com.supportportal.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;

import static com.supportportal.constant.FileConstant.FILE_PATH;
import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/document"})
public class DocumentResource {
    private UserService userService;
    private ServletContext servletContext;
    private DocumentService documentService;
private DocumentRepository documentRepository;

    public static final String SPRINT_DELETED_SUCCESSFULLY = "Sprint deleted successfully";
    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    public DocumentResource(UserService userService, ServletContext servletContext, DocumentService documentService,DocumentRepository documentRepository) {
        this.userService = userService;
        this.servletContext = servletContext;
        this.documentService = documentService;
        this.documentRepository=documentRepository;

    }


    //private static final String FILE_PATH = "D:\\pfe-workspace\\PFERestApi\\Document\\";
    private static final String DEFAULT_FILE_NAME = "java-tutorial.pdf";


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            documentService.store(file);

            message = "upload de fichier  " + file.getOriginalFilename() + "avec success !";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "erreur upload  " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

  /*  @PostMapping("/documents")
    public Document save(@RequestBody Document document) {
        //String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        document.setUtilisateur(utilisateurService.findUserByUsername(currentPrincipalName));
        document.setDateCreation(new Date());
        return documentService.save(document);
    }*/

    @PostMapping("/docSave")
    public ResponseEntity<Document> save(@RequestBody Document document) {
        Document addedDocument=documentService.save(document.getId(),document.getDateCreation(),
                document.getDescription(),document.getNomDocument(),document.getFile(),
                document.getTypeDocument(),document.isArchive(),document.isEtat(),document.getDocUser());

        return new ResponseEntity<>(addedDocument,OK);
    }



    @GetMapping("/documentsPublic")
    public List<Document> getAllPublic() {
        LOGGER.info(documentRepository.findDocumentByArchiveFalseAndEtatTrue().toString());

        return documentService.findDocumentByArchiveFalseEtatPublic();
    }

    @GetMapping("/docById/{id}")
    public Document getDocumentById(@PathVariable("id") Long id) {
        return documentService.getDocumentById(id);
    }

    @GetMapping("/prive")
    public List<Document> getAllDocumentPrivate() {
        return documentService.findDocumentByArchiveFalseEtatPrive();
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<InputStreamResource> handleFileDownload(@PathVariable(value = "fileName") String fileName, HttpServletResponse response) throws IOException {
        //Resource resource = documentService.loadFileAsResource(fileName);
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        // Try to determine file's content type

        File file = new File(FILE_PATH +  "/Document/"+ fileName);
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








    public ResponseEntity<InputStreamResource> download(@RequestParam(value="fileName", defaultValue=DEFAULT_FILE_NAME) String fileName) throws FileNotFoundException, FileNotFoundException {
        String fullPath = FILE_PATH+"/Document/" + fileName;
        File file = new File(fullPath);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.add("Content-Disposition", "attachment;filename=\""+fileName+"\"");
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<InputStreamResource>(inputStreamResource,httpHeaders,HttpStatus.OK);
    }

    @GetMapping("/totale")
    public int totalDocument() {
        return documentService.totalDocument();
    }

    @GetMapping("/totaleArchive")
    public int totalDocumentArchived() {
        return documentService.totalDocumentArchived();
    }
    @GetMapping("/totaleNonArchive")
    public int totalDocumentNonArchive() {
        return documentService.totalDocumentNonArchive();
    }

    @GetMapping("/totalePrive")
    public int totalDocumentPrive() {
        return documentService.totalDocumentPrive();
    }

    @GetMapping("/totalePublic")
    public int totalDocumentPublic() {
        return documentService.totalDocumentPublic();
    }




    @GetMapping("/archives")
    public List<Document> getAllArchivedDocument() {
        return documentService.findDocumentByArchiveTrue();
    }

    @DeleteMapping("/deleteDoc/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);

    }

    @PutMapping("/archive/{id}")
    public Document archiveDocument(@PathVariable("id") Long id, @RequestBody Document document) {
        document.setId(id);
        document.setArchive(true);
        return documentService.updateDocument(document);
    }

    @PutMapping("/restaurer/{id}")
    public Document restaurerDocument(@PathVariable("id") Long id, @RequestBody Document document) {
        document.setId(id);
        document.setArchive(false);
        return documentService.updateDocument(document);
    }
    @GetMapping("/docprive/username/{username}")
    public List<Document> getDocumentPriveByUsername(@PathVariable("username") String username) {

        User utilisateur = userService.findUserByUsername(username);
        return documentService.findDocumentByUsername(utilisateur);
    }

    @GetMapping("/docArchive/username/{username}")
    public List<Document> getDocumentArchiveByUsername(@PathVariable("username") String username) {

        User utilisateur = userService.findUserByUsername(username);
        return documentService.findDocArchiveByUtilisateur(utilisateur);
    }



    @PutMapping("/docEdit/{id}")
    public Document updateDocument(@PathVariable ("id") Long id,@RequestBody Document document) {
        document.setId(id);
        return documentService.updateDocument(document);
    }
    @GetMapping("/documents/Auth/{username}")
    public List<Document> getDocByUser(@PathVariable("username") String username) {

        return documentService.findByUtilisateur(userService.findUserByUsername(username));
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }

}

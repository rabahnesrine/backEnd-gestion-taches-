package com.supportportal.resource;

import com.supportportal.domain.Video;
import com.supportportal.repository.VedioRepository;
import com.supportportal.service.UserService;
import com.supportportal.service.VedioService;
import com.supportportal.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.supportportal.constant.FileConstant.FILE_PATH;
import static com.supportportal.constant.FileConstant.FORWARD_SLASH;
import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/video"})
public class VedioRessource {
    private static final String VEDIO = "video";
    private UserService userService;
    private ServletContext servletContext;
    private VedioService vedioService;
    private VedioRepository vedioRepository;

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public VedioRessource(UserService userService, ServletContext servletContext, VedioService vedioService, VedioRepository vedioRepository) {
        this.userService = userService;
        this.servletContext = servletContext;
        this.vedioService = vedioService;
        this.vedioRepository=vedioRepository;

    }


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            vedioService.store(file);

            message = "upload de fichier  " + file.getOriginalFilename() + "avec success !";
            LOGGER.info("yes"+file.getBytes());

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "erreur upload  " + file.getOriginalFilename() + "!";
            LOGGER.info("non");

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


/*
    @PostMapping("/add")
    public ResponseEntity<Video> addNewMp4(@RequestParam("nomVedio") String nomVedio,
                                           @RequestParam("authorisation")String authorisation,
                                           @RequestParam("description")String description,

                                           @RequestParam( value = "file",required = false) MultipartFile file ) {
        Video newVideo =vedioService.addMp4(nomVedio,authorisation,description,file);
        return new ResponseEntity<Video>(newVideo,OK);
    }*/


    @PostMapping("/vedioSave")
    public ResponseEntity<Video> save(@RequestBody Video video) {
        Video addedVideo =vedioService.save(video.getId(), video.getNomVedio(), video.getAuthorisation(), video.getDescription(), video.getFile()           );

        return new ResponseEntity<>(addedVideo,OK);
    }
    @GetMapping("/getALLVedio")
    public ResponseEntity<List<Video>> getAllVedio(){
        List<Video> allvedio=vedioRepository.findAll();
        return new ResponseEntity<>(allvedio,OK);

    }


    @GetMapping(path="/vedioshow/{file}")
    public byte[] getVedio(@PathVariable("file") String file) throws IOException {
        return Files.readAllBytes(Paths.get(FILE_PATH+FORWARD_SLASH+VEDIO+FORWARD_SLASH+file));
    }

    @GetMapping(path="/vediosh/{file}",produces="video/mp4")
    public
    FileSystemResource getVedio2(@PathVariable("file") String file) throws IOException {
        LOGGER.info("req");
        LOGGER.info(System.getProperty(FILE_PATH+FORWARD_SLASH+VEDIO+FORWARD_SLASH+file));
        return new FileSystemResource(Paths.get(FILE_PATH+FORWARD_SLASH+VEDIO+FORWARD_SLASH+file));
    }

}

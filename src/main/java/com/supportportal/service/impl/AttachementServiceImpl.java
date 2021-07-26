package com.supportportal.service.impl;

import com.supportportal.domain.*;
import com.supportportal.repository.AttachementRepository;
import com.supportportal.repository.TaskRepository;
import com.supportportal.service.AttachementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class AttachementServiceImpl implements AttachementService {
    private AttachementRepository attachementRepository;
private TaskRepository taskRepository;
    private final Path rootLocation = Paths.get("C:/Users/USER/supportportal/Attachement");

   @Autowired
    public AttachementServiceImpl(AttachementRepository attachementRepository, TaskRepository taskRepository ) {
        this.attachementRepository = attachementRepository;
       this.taskRepository=taskRepository;

   }

    @Override
    public void store(MultipartFile file) {
        try {
            System.out.println(file.getOriginalFilename());
            System.out.println(rootLocation.toUri());
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }




   /* public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            System.out.println("filePath : Service"+ filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                System.out.println("resource : Service"+ resource);
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
    */



    @Override
    public Attachement saveAttachement(Attachement attachement) {
        return attachementRepository.save(attachement);
    }

    @Override
    public List<Attachement> findAttachementByIdTask(Long idTask) {

        Task findTask= new Task();
        findTask=taskRepository.findTaskByIdTask(idTask);

        return attachementRepository.findAttachementByTaskAtt(findTask);
    }


    @Override
    public List<Attachement> findAllAttachement() {
        return attachementRepository.findAll();
    }

  /*  @Override
    public void deleteAttachementById(Long idAttachement) {
       attachementRepository.deleteById(idAttachement);

    }*/


    @Override
    public void deleteAtt(Long idAttachement) {
        Attachement findAtt= new Attachement();
        findAtt=attachementRepository.findAttachementByIdAttachement(idAttachement);

        attachementRepository.deleteById(findAtt.getIdAttachement());

    }




    @Override
    public Attachement saveAtt(long idAttachement, String file, Date dateCreation, Task tacheAtt) {

        Attachement newAtt = new Attachement();
        newAtt.setIdAttachement(idAttachement);

        int length = file.length();
        String path = file.substring(12, length);
        newAtt.setFile(path);

        newAtt.setTaskAtt(tacheAtt);
        newAtt.setDateCreation (new Date());

        return attachementRepository.save(newAtt);



    }




}

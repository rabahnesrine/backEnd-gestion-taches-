package com.supportportal.service;

import com.supportportal.domain.Attachement;
import com.supportportal.domain.Task;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.Date;
import java.util.List;

public interface AttachementService {
    void store(MultipartFile file);

    Resource loadFile(String filename);

    Attachement saveAttachement(Attachement attachement);

    List<Attachement> findAttachementByIdTask(Long idTask);

    List<Attachement> findAllAttachement();

    //void deleteAttachementById(Long idAttachement);
     void deleteAtt(Long idAttachement) ;



  Attachement saveAtt(long idAttachement, String file, Date dateCreation, Task tacheAtt);



    }

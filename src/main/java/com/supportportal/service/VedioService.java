package com.supportportal.service;

import com.supportportal.domain.Video;
import org.springframework.web.multipart.MultipartFile;

public interface VedioService {

    public void store(MultipartFile file);

    Video save(long id, String nomVedio, String authorisation, String description, String file);
   // Video addMp4(String nomVedio, String authorisation, String description, MultipartFile file) ;

}

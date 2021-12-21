package com.supportportal.service.impl;

import com.supportportal.domain.Video;
import com.supportportal.repository.VedioRepository;
import com.supportportal.service.VedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static com.supportportal.constant.FileConstant.*;


@Service
public class vedioServiceImpl implements VedioService {

    private final Path rootLocation = Paths.get("C:/Users/USER/supportportal/video");



    @Autowired
    private VedioRepository vedioRepository;

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





    @Override
    public Video save(long id, String nomVedio, String authorisation, String description, String file) {
        Video newVideo = new Video();
        newVideo.setId(id);
        int length = file.length();
        String path = file.substring(12, length);
        newVideo.setDateCreation(new Date());
        newVideo.setDescription(description);
        newVideo.setNomVedio(nomVedio);

        newVideo.setAuthorisation(authorisation);

        newVideo.setFile(setVideoUrl(path));

        return vedioRepository.save(newVideo);

    }
    private String setVideoUrl(String nomFile) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_VIDEO_PATH + nomFile ).toUriString();
    }
/*
@Override
public Vedio addMp4(String nomVedio,String authorisation,String description, MultipartFile file) {
    Vedio newVedio = new Vedio();


    newVedio.setDateCreation(new Date());
    newVedio.setDescription(description);
    newVedio.setNomVedio(nomVedio);

    newVedio.setAuthorisation(authorisation);
    int length = file.getName().length();
    String path = file.getName().substring(12, length);
    newVedio.setFile(path);

    return vedioRepository.save(newVedio);
}*/
}
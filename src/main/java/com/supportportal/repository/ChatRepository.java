package com.supportportal.repository;

import com.supportportal.domain.Chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,String> {

    Page<Chat> findAll(Pageable pageable);
    ArrayList<Chat> findAll();
    Chat findTopByOrderByIdDesc();
    ArrayList<Chat> findByIdGreaterThan(int id);
}

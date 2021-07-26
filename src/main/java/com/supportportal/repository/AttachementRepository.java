package com.supportportal.repository;

import com.supportportal.domain.Attachement;
import com.supportportal.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachementRepository extends JpaRepository<Attachement,Long> {
   public List<Attachement> findAttachementByTaskAtt(Task task);
    public Attachement findAttachementByIdAttachement(long idAttachement);
}

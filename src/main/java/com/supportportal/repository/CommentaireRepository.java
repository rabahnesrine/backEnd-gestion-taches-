package com.supportportal.repository;

import com.supportportal.domain.Commentaire;
import com.supportportal.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {
    List<Commentaire> findCommentaireByTacheCom(Task task);
    public Commentaire findCommentaireByIdCommentaire(long idCommentaire);


}

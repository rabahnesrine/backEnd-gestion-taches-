package com.supportportal.service;

import com.supportportal.domain.Commentaire;

import java.util.List;

public interface CommentaireService {
    Commentaire saveComment(Commentaire commentaire);
    List<Commentaire> findCommentaireByTaskId(Long idTask);
    void deleteCommentaireById(Long idCommentaire);
    List<Commentaire> findAllCommentaire();


}

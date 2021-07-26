package com.supportportal.service.impl;

import com.supportportal.domain.Commentaire;
import com.supportportal.domain.Task;
import com.supportportal.repository.CommentaireRepository;
import com.supportportal.repository.TaskRepository;
import com.supportportal.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireServiceImpl implements CommentaireService {
    private CommentaireRepository commentaireRepository;
    private TaskRepository taskRepository;

    @Autowired
    public CommentaireServiceImpl(CommentaireRepository commentaireRepository,TaskRepository taskRepository) {
        this.commentaireRepository = commentaireRepository;
       this.taskRepository=taskRepository;
    }

    @Override
    public Commentaire saveComment(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @Override
    public List<Commentaire> findCommentaireByTaskId(Long idTask) {
        Task findTask= new Task();
        findTask=taskRepository.findTaskByIdTask(idTask);


        return commentaireRepository.findCommentaireByTacheCom(findTask);
    }

    @Override
    public void deleteCommentaireById(Long idCommentaire) {
         commentaireRepository.deleteById(idCommentaire);


    }

    @Override
    public List<Commentaire> findAllCommentaire() {
        return commentaireRepository.findAll();
    }
}

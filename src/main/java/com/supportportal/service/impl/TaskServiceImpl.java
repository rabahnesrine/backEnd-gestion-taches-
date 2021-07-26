package com.supportportal.service.impl;

import com.supportportal.domain.*;

import com.supportportal.enumeration.ETAT;
import com.supportportal.enumeration.TASKETAT;
import com.supportportal.repository.AttachementRepository;
import com.supportportal.repository.CommentaireRepository;
import com.supportportal.repository.TaskRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private AttachementRepository attachementRepository;

    private CommentaireRepository commentaireRepository;
    private UserRepository userRepository;

     @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, AttachementRepository attachementRepository, CommentaireRepository commentaireRepository,UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.attachementRepository = attachementRepository;
        this.commentaireRepository = commentaireRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Task addNewTask(long idTask, String nameTask, Date dateFin, String description, String etatTask,
                           User taskCreePar, Sprint sprint, User memberAffecter, boolean archive) {
Task newTask = new Task();
        newTask.setDateFin(dateFin);
        newTask.setIdTask(idTask);
        newTask.setNameTask(nameTask);
        newTask.setEtatTask(getTaskEtatEnumName(etatTask).name());
        newTask.setMemberAffecter(memberAffecter);
        newTask.setSprint(sprint);
        newTask.setDescription(description);
        newTask.setTaskCreePar(taskCreePar);
        newTask.setDateCreation (new Date());
        newTask.setArchive(archive);


        return taskRepository.save(newTask);
    }


    @Override
    public Task updateTask(long idTask, String nameTask, Date dateCreation, Date dateFin, String description, String etatTask, User taskCreePar, Sprint sprint, User memberAffecter, boolean archive) {

        Task updatedTask = new Task();
        updatedTask.setDateFin(dateFin);
        updatedTask.setDateCreation(dateFin);
        updatedTask.setIdTask(idTask);
        updatedTask.setNameTask(nameTask);
        updatedTask.setEtatTask(getTaskEtatEnumName(etatTask).name());
        updatedTask.setMemberAffecter(memberAffecter);
        updatedTask.setSprint(sprint);
        updatedTask.setDescription(description);
        updatedTask.setTaskCreePar(taskCreePar);
        updatedTask.setDateModification(new Date());
        updatedTask.setArchive(archive);


        return taskRepository.save(updatedTask);

    }

  /*  @Override
    public Task save(Task task) {
        if (!task.isArchive())
            task.setArchive(false);
        return taskRepository.save(task);
    }*/

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long idTask) {
       /* System.out.println("id task" + idTask);
        List<Commentaire> listeComment=  commentaireRepository.findCommentaireByTacheCom(idTask);
        List<Attachement> listeTaskAtt = attachementRepository.findAttachementByTaskAtt(idTask);
        listeTaskAtt.forEach(a ->attachementRepository.deleteById(a.getIdAttachement()) );

        for (Commentaire c:listeComment ) {
            commentaireRepository.deleteById(c.getIdCommentaire());
        }*/

        taskRepository.deleteById(idTask);

    }


    @Override
    public int getTotalTaskUnstarted() {
        return taskRepository.getTotalTaskUnstarted();
    }

    @Override
    public int getTotalTaskCancel() {
        return taskRepository.getTotalTaskCancel();
    }

    @Override
    public int getTotalTaskToFinish() {
        return taskRepository.getTotalTaskToFinish();
    }

    @Override
    public int getTotalTaskInProgress() {
        return taskRepository.getTotalTaskInProgress();
    }

    @Override
    public Task findTaskByIdTask(long idTask) {
       return taskRepository.findTaskByIdTask(idTask);
       // return taskRepository.getOne(idTask);

    }

    @Override
    public int totalTask() {
        return taskRepository.totalTask();
    }

    @Override
    public int totalTaskArchived() {
        return taskRepository.totalTaskArchived();
    }



    @Override
    public List<Task> findTaskByTaskCreePar(User user) {
        return taskRepository.findTaskByTaskCreeParAndArchiveFalse(user.getId());
    }




  /*  @Override
    public List<Task> findTaskByTaskCreePar(User user) {
      String username=user.getUsername();
     com.supportportal.domain.User user1 = userRepository.findUserByUsername(username);
        return taskRepository.findTaskByTaskCreeParAndArchiveFalse(user1.getId());
    }*/

    @Override
    public int findTotalByCreeParId(User user) {
        return taskRepository.findTotalByCreeParId(user.getId());
    }

    @Override
    public List<Task> findTaskByMemberAffecter(User user) {
        return taskRepository.findTaskByMemberAffecterAndArchiveFalse(user.getId());
    }

    @Override
    public int findTotalByMemberAffecterId(User user) {
        return taskRepository.findTotalByMemberAffecterId(user.getId());
    }




    @Override
    public List<Task> findTaskByArchiveTrue() {
        return taskRepository.findTaskByArchiveTrue();
    }

    @Override
    public List<Task> findTaskByArchiveFalse() {
        return taskRepository.findTaskByArchiveFalse();
    }

    @Override
    public Task findTaskByNameTask(String nameTask) {
        return taskRepository.findTaskByNameTask(nameTask);
    }



    private TASKETAT getTaskEtatEnumName(String etat) {
        return TASKETAT.valueOf(etat.toUpperCase());
    }
}

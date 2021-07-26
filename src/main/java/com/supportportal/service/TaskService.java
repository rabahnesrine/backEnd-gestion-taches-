package com.supportportal.service;

import com.supportportal.domain.*;


import java.util.Date;
import java.util.List;

public interface TaskService {
   Task addNewTask(long idTask, String nameTask, Date dateFin, String description, String etatTask, User taskCreePar, Sprint sprint,
                    User memberAffecter, boolean archive) ;

    Task updateTask(long idTask, String nameTask, Date dateCreation, Date dateFin, String description, String etatTask, User taskCreePar, Sprint sprint,
                    User memberAffecter, boolean archive);



 //   Task save(Task task);
    List<Task> findAll();
    void deleteTask(Long idTask);

    int getTotalTaskUnstarted();
    int getTotalTaskCancel();
    int getTotalTaskToFinish();
    int getTotalTaskInProgress();

    Task findTaskByIdTask(long idTask);
    int totalTask();

    int totalTaskArchived();

    List<Task> findTaskByTaskCreePar(User user);
    int findTotalByCreeParId(User user);

    List<Task> findTaskByMemberAffecter(User user);
    int findTotalByMemberAffecterId(User user);

    List<Task> findTaskByArchiveTrue();
    List<Task> findTaskByArchiveFalse();
    Task findTaskByNameTask(String nameTask);

}

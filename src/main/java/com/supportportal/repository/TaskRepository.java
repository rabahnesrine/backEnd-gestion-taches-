package com.supportportal.repository;

import com.supportportal.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {



    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='TASKETAT_UNSTARTED'")
    int getTotalTaskUnstarted();

    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='TASKETAT_CANCEL'")
    int getTotalTaskCancel();

    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='TASKETAT_COMPLETED'")
    int getTotalTaskToFinish();

    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='TASKETAT_INPROGRESS'")
    int getTotalTaskInProgress();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.archive=false ")
    int totalTask();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.archive=true ")
    int totalTaskArchived();

    //for createur
  List<Task> findTaskByTaskCreeParAndArchiveFalse(Long id);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.taskCreePar.id=:id")
    int findTotalByCreeParId(@Param("id") Long id);

    // for member affecter
    List<Task> findTaskByMemberAffecterAndArchiveFalse(Long id);
    List<Task> findTaskByMemberAffecterId(Long id);
    @Query("SELECT COUNT(t) FROM Task t WHERE t.memberAffecter.id=:id")
    int findTotalByMemberAffecterId(@Param("id") Long id);
// nb task by projet
//@Query("SELECT COUNT(t) FROM Task t WHERE t.sprint.projet.idProjet=:idProjet")
//int findTotalBySprintProjetId(@Param("idProjet") Long idProjet);

//nb task byy sprint
    @Query("SELECT COUNT(t) FROM Task t WHERE t.sprint.idSprint=:idSprint")
    int findTotalBySprintId(@Param("idSprint") Long idSprint);


    @Query("SELECT COUNT(t) FROM Task t WHERE t.sprint.idSprint=:idSprint and t.etatTask ='TASKETAT_COMPLETED'")
    int findTotalCompletedBySprintId(@Param("idSprint") Long idSprint);


 public Task findTaskByNameTask(String nameTask);
    public Task findTaskByIdTask(long idTask);

    List<Task> findTaskByArchiveTrue();
    List<Task> findTaskByArchiveFalse();

}

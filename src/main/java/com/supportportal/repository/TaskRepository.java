package com.supportportal.repository;

import com.supportportal.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {



    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='Unstarted'")
    int getTotalTaskUnstarted();

    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='Cancel'")
    int getTotalTaskCancel();

    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='To Finish'")
    int getTotalTaskToFinish();

    @Query("SELECT COUNT(t.etatTask) FROM Task t WHERE t.etatTask ='In Progress'")
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

    @Query("SELECT COUNT(t) FROM Task t WHERE t.memberAffecter.id=:id")
    int findTotalByMemberAffecterId(@Param("id") Long id);



 public Task findTaskByNameTask(String nameTask);
    public Task findTaskByIdTask(long idTask);

    List<Task> findTaskByArchiveTrue();
    List<Task> findTaskByArchiveFalse();

}

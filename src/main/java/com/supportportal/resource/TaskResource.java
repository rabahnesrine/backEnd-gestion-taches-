package com.supportportal.resource;

import com.supportportal.domain.*;
import com.supportportal.service.CommentaireService;
import com.supportportal.service.TaskService;
import com.supportportal.service.UserService;
import com.supportportal.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path={"/task"})
public class TaskResource {
    public static final String TASK_DELETED_SUCCESSFULLY = "Task deleted successfully";
    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

    private TaskService taskService;

    private UserService userService;

    private CommentaireService commentaireService;


    @Autowired
    public TaskResource(TaskService taskService, UserService userService, CommentaireService commentaireService) {
        this.taskService = taskService;
        this.userService = userService;
        this.commentaireService = commentaireService;
    }

   /* @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentaireService commentaireService;*/

   // @Autowired
  //  private ProfileService profileService;


    @PostMapping("/save")
    public ResponseEntity<Task> addNewTask(@RequestBody Task newTask) {
        User u = newTask.getTaskCreePar();
        User user = userService.findUserByUserId(u.getUserId());
        //  newSprint.setChefEquipe(user);
        Task addedTask=taskService.addNewTask(newTask.getIdTask(),
                newTask.getNameTask(), newTask.getDateFin(),newTask.getDescription(),
                newTask.getEtatTask(),newTask.getTaskCreePar(),newTask.getSprint(), newTask.getMemberAffecter(),
                newTask.isArchive() );
        return new ResponseEntity<>(addedTask,OK);

    }





    @PutMapping("/update/{idTask}")
    public ResponseEntity<Task> updateTask(@PathVariable long idTask, @RequestBody Task updatedTask) {
        Task currentTask=this.taskService.findTaskByIdTask(idTask);
        updatedTask.setIdTask(idTask);
        updatedTask.setDateModification(new Date());

        Task updateTask=taskService.updateTask(updatedTask.getIdTask(),
                updatedTask.getNameTask(),updatedTask.getDateCreation(),updatedTask.getDateFin(),updatedTask.getDescription(),
                updatedTask.getEtatTask(),
                updatedTask.getTaskCreePar(), updatedTask.getSprint(), updatedTask.getMemberAffecter(),updatedTask.isArchive() );
        return new ResponseEntity<>(updateTask,OK);
    }





  /*  @PostMapping("/save")
    public Task saveTache(@RequestBody Task task) {

        task.setDateCreation(new Date());
      //  task.setDateModification(new Date());
        return taskService.save(task);

    }

    @PutMapping("/update/{idTask}")
    public Task updateTask(@PathVariable Long idTask, @RequestBody Task task) {
        task.setIdTask(idTask);
        task.setDateModification(new Date());
        System.out.println("update Tache --- ");
        System.out.println(task.getEtatTask());
        return taskService.save(task);
    }*/

    @GetMapping("/all")
    public List<Task> findAllTasks(){
        return  taskService.findAll();
    }


    @GetMapping("/allNonArchive")
    public List<Task> findAllTaskNonArchive() {
        return taskService.findTaskByArchiveFalse();

    }

    @DeleteMapping("/delete/{idTask}")
    @PreAuthorize("hasAnyAuthority('task:delete')")

    public ResponseEntity<HttpResponse> deleteTask(@PathVariable("idTask") Long idTask) {
        taskService.deleteTask(idTask);
        return  response(NO_CONTENT, TASK_DELETED_SUCCESSFULLY);

    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }


    @DeleteMapping("/deleteComm/{idCommentaire}")
    public void deleteCommentaire(@PathVariable("idCommentaire") Long idCommentaire) {
        commentaireService.deleteCommentaireById(idCommentaire);
    }
    @GetMapping("/inProgress")
    public int getTotalTaskInProgress() {
        return taskService.getTotalTaskInProgress();
    }

    @GetMapping("/toFinish")
    public int getTotalTaskToFinish() {
        return taskService.getTotalTaskToFinish();
    }

    @GetMapping("/unstarted")
    public int getTotalTaskUnstarted() {
        return taskService.getTotalTaskUnstarted();
    }

    @GetMapping("/cancel")
    public int getTotalTaskCancel() {
        return taskService.getTotalTaskCancel();
    }

    @GetMapping("/findById/{idTask}")
    public Task findTaskById(@PathVariable("idTask") Long idTask) {
        return taskService.findTaskByIdTask(idTask);
    }

    @PostMapping("/commentSave")
    public Commentaire saveComment(@RequestBody Commentaire commentaire) {
        //String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        commentaire.setUserCom(userService.findUserByUsername(currentPrincipalName));
        commentaire.setDateComment(new Date());
        
        return commentaireService.saveComment(commentaire);
    }

    @GetMapping("/total")
    public int totalTask() {
        return taskService.totalTask();
    }

    @GetMapping("/totalArchive")
    public int totalTaskArchived() {
        return taskService.totalTaskArchived();
    }

    // not important

    @GetMapping("/Auth/{username}")
    public List<Task> getTaskByChef(@PathVariable("username") String username) {

        return taskService.findTaskByTaskCreePar(userService.findUserByUsername(username));
    }



    @GetMapping("/comment/{idTask}")
    public List<Commentaire> getCommentByTaskId(@PathVariable("idTask") Long idTask) {
LOGGER.info(commentaireService.findCommentaireByTaskId(idTask).toString());
        return commentaireService.findCommentaireByTaskId(idTask);
    }

    @GetMapping("/totalByMember/{username}")
    public int totalTacheByMemberAffected(@PathVariable("username") String username) {
        return taskService.findTotalByMemberAffecterId(userService.findUserByUsername(username));
    }


    @GetMapping("/totalByChef/{username}")
    public int totalTacheByCreePar(@PathVariable("username") String username) {
        return taskService.findTotalByCreeParId(userService.findUserByUsername(username));
    }

    @GetMapping("archives")
    public List<Task> getArchivedTask() {
        return taskService.findTaskByArchiveTrue();
    }

    @PutMapping("/archive/{idTask}")
    public void archiveTask(@PathVariable Long idTask, @RequestBody Task task) {
        task.setIdTask(idTask);
        task.setArchive(true);
        System.out.println("task archived !!!");
        taskService.updateTask(task.getIdTask(),
                task.getNameTask(),task.getDateCreation(),task.getDateFin(),task.getDescription(),
                task.getEtatTask(),
                task.getTaskCreePar(), task.getSprint(), task.getMemberAffecter(),task.isArchive());
    }

    @PutMapping("/restore/{id}")
    public void restoreTask(@PathVariable Long idTask, @RequestBody Task task) {
        task.setIdTask(idTask);
        task.setArchive(false);
        System.out.println("task restaured !!!");
        taskService.updateTask(task.getIdTask(),
                task.getNameTask(),task.getDateCreation(),task.getDateFin(),task.getDescription(),
                task.getEtatTask(),
                task.getTaskCreePar(), task.getSprint(), task.getMemberAffecter(),task.isArchive());
    }

    // deleted peut etre
    @GetMapping("/contactChef/{id}")
    public List<Task> getTaskCreePar(@PathVariable("id") Long id) {
      //  Profile profile = profileService.getProfileById(id);
       User chef= userService.findUserById(id);
        return taskService.findTaskByTaskCreePar(chef);
    }

    @GetMapping("/contactMember/{id}")
    public List<Task> getTaskMember(@PathVariable("id") Long id) {
        //  Profile profile = profileService.getProfileById(id);
        User member= userService.findUserById(id);
        return taskService.findTaskByMemberAffecter(member);
    }
}

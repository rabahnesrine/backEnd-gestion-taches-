package com.supportportal.resource;

import com.supportportal.domain.*;
import com.supportportal.repository.TaskRepository;
import com.supportportal.service.ProjetService;
import com.supportportal.service.SprintService;
import com.supportportal.service.TaskService;
import com.supportportal.service.UserService;
import com.supportportal.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/sprint"})
public class SprintResource {
    private UserService userService;
    private SprintService sprintService;
    private ProjetService projetService;
    private TaskService taskService;
    private TaskRepository taskRepository;

    public static final String SPRINT_DELETED_SUCCESSFULLY = "Sprint deleted successfully";
    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);


    public SprintResource(UserService userService, ProjetService projetService,TaskRepository taskRepository,TaskService taskService, SprintService sprintService) {
        this.userService = userService;
        this.sprintService = sprintService;
        this.projetService = projetService;
       this.taskService=taskService;
        this.taskRepository= taskRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Sprint> addNewSprint(@RequestBody Sprint newSprint) {
        User u = newSprint.getSprintCreePar();
        User user = userService.findUserByUserId(u.getUserId());
        //  newSprint.setChefEquipe(user);

        Sprint addedSprint = sprintService.addNewSprint(newSprint.getIdSprint(),
                newSprint.getNomSprint(), newSprint.getDateFin(), newSprint.getDescription(),
                newSprint.getEtatSprint(), newSprint.getProjet(),
                newSprint.getSprintCreePar(), newSprint.getChefAffecter());
        return new ResponseEntity<>(addedSprint, OK);

    }


    @PutMapping("/update/{idSprint}")
    public ResponseEntity<Sprint> updateSprint(@PathVariable long idSprint, @RequestBody Sprint updatedSprint) {
        Sprint currentSprint = this.sprintService.findSprintByIdSprint(idSprint);
        updatedSprint.setIdSprint(idSprint);
        updatedSprint.setDateModification(new Date());

        Sprint updateSprint = sprintService.updateSprint(updatedSprint.getIdSprint(),
                updatedSprint.getNomSprint(), updatedSprint.getDateCreation(), updatedSprint.getDateFin(), updatedSprint.getDescription(),
                updatedSprint.getEtatSprint(), updatedSprint.getProjet(),
                updatedSprint.getSprintCreePar(), updatedSprint.getChefAffecter());
        return new ResponseEntity<>(updateSprint, OK);
    }


    @GetMapping("/all")
    //@PreAuthorize("hasAnyAuthority('sprint:read')")
    public ResponseEntity<List<Sprint>> getSprints() {
        List<Sprint> sprints = sprintService.getSprints();
        return new ResponseEntity<>(sprints, OK);

    }


    @DeleteMapping("/delete/{idSprint}")
    @PreAuthorize("hasAnyAuthority('sprint:delete')")
    public ResponseEntity<HttpResponse> deleteSprint(@PathVariable("idSprint") long idSprint) {
        sprintService.deleteSprint(idSprint);
        return response(NO_CONTENT, SPRINT_DELETED_SUCCESSFULLY);

    }

    @GetMapping("/findbynom/{nomSprint}")
    public ResponseEntity<Sprint> findtSprintByNomSprint(@PathVariable("nomSprint") String nomSprint) {
        Sprint sprint = sprintService.findSprintByNomSprint(nomSprint);
        return new ResponseEntity<>(sprint, OK);

    }

    @GetMapping("/findbyid/{idSprint}")
    public ResponseEntity<Sprint> findSprintByIdSprint(@PathVariable("idSprint") long idSprint) {
        Sprint sprint = sprintService.findSprintByIdSprint(idSprint);
        return new ResponseEntity<>(sprint, OK);

    }

    @GetMapping("/projetSprint/{idProjet}")
    public List<Sprint> findSprintByProjet(@PathVariable("idProjet") Long idProjet) {
        return sprintService.findSprintByProjetId(idProjet);
    }

    @GetMapping("/totalByProjet/{idProjet}")
    public int totalSprintByProjet(@PathVariable("idProjet") long idProjet) {
        return sprintService.findTotalByProjetId(projetService.findProjetByIdProjet(idProjet));
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()), httpStatus);
    }


    @GetMapping("/development")
    public int getTotalSprintDevelopment() {
        return this.sprintService.getTotalSprintDevelopment();
    }

    @GetMapping("/delivery")
    public int getTotalSprintDelivery() {
        return this.sprintService.getTotalSprintDelivery();
    }

    @GetMapping("/testing")
    public int getTotalSprintTesting() {
        return this.sprintService.getTotalSprintTesting();
    }

    @GetMapping("/paused")
    public int getTotalSprintPaused() {
        return this.sprintService.getTotalSprintPaused();
    }

    @GetMapping("/totalSprint")
    public int totalSprint() {
        return this.sprintService.totalSprint();
    }


    @GetMapping("/sprintBytaskMember/{id}")
    public List<Sprint> getSprintBytaskMember(@PathVariable("id") Long id) {
        User member = userService.findUserById(id);
     //   List<Task> taskMember = this.taskService.findTaskByMemberAffecter(member);
        List<Task> taskMember = taskRepository.findTaskByMemberAffecterId(id);

        List<Sprint> sprintsNonFiltre = new ArrayList<>();
        List<Sprint> filteredListSprint = new ArrayList<>();
        for (Task task : taskMember) {
            sprintsNonFiltre.add(task.getSprint());
            // break;  return filteredListSprint.size();
        }
LOGGER.info(taskMember.toString());
        for (Sprint sprintfixe : sprintsNonFiltre) {

            for (Sprint sprint : sprintsNonFiltre) {
                if (sprint.getIdSprint() == (sprintfixe.getIdSprint()) &&(filteredListSprint.indexOf(sprintfixe)== -1 )) {
                    filteredListSprint.add(sprint);
                    // break;  return filteredListSprint.size();
                }
            }
        }
LOGGER.info(filteredListSprint.toString());
       return filteredListSprint;
    }}


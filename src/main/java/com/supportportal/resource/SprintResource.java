package com.supportportal.resource;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.Sprint;
import com.supportportal.domain.User;
import com.supportportal.service.SprintService;
import com.supportportal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/sprint"})
public class SprintResource {
    private UserService userService;
    private SprintService sprintService;
    public static final String SPRINT_DELETED_SUCCESSFULLY = "Sprint deleted successfully";


    public SprintResource(UserService userService, SprintService sprintService) {
        this.userService = userService;
        this.sprintService = sprintService;
    }

    @PostMapping("/add")
    public ResponseEntity<Sprint> addNewSprint(@RequestBody Sprint newSprint) {
        User u = newSprint.getSprintCreePar();
        User user = userService.findUserByUserId(u.getUserId());
        //  newSprint.setChefEquipe(user);

        Sprint addedSprint=sprintService.addNewSprint(newSprint.getIdSprint(),
        newSprint.getNomSprint(),newSprint.getDateFin(),newSprint.getDescription(),
        newSprint.getEtatSprint(), newSprint.getProjet(),
        newSprint.getSprintCreePar(), newSprint.getChefAffecter() );
        return new ResponseEntity<>(addedSprint,OK);

    }





    @PutMapping("/update/{idSprint}")
   public ResponseEntity<Sprint> updateSprint(@PathVariable long idSprint, @RequestBody Sprint updatedSprint) {
Sprint currentSprint=this.sprintService.findSprintByIdSprint(idSprint);
        updatedSprint.setIdSprint(idSprint);
        updatedSprint.setDateModification(new Date());

        Sprint updateSprint=sprintService.updateSprint(updatedSprint.getIdSprint(),
                updatedSprint.getNomSprint(),updatedSprint.getDateCreation(),updatedSprint.getDateFin(),updatedSprint.getDescription(),
                updatedSprint.getEtatSprint(), updatedSprint.getProjet(),
                updatedSprint.getSprintCreePar(), updatedSprint.getChefAffecter() );
        return new ResponseEntity<>(updateSprint,OK);
    }


    @GetMapping("/all")
    //@PreAuthorize("hasAnyAuthority('sprint:read')")
    public ResponseEntity<List<Sprint>> getSprints(){
        List<Sprint> sprints= sprintService.getSprints();
        return new ResponseEntity<>(sprints,OK);

    }



    @DeleteMapping("/delete/{idSprint}")
     @PreAuthorize("hasAnyAuthority('sprint:delete')")
    public ResponseEntity<HttpResponse> deleteSprint(@PathVariable("idSprint") long  idSprint){
        sprintService.deleteSprint(idSprint);
        return  response(NO_CONTENT, SPRINT_DELETED_SUCCESSFULLY);

    }

    @GetMapping("/findbynom/{nomSprint}")
    public  ResponseEntity<Sprint> findtSprintByNomSprint(@PathVariable("nomSprint") String nomSprint) {
        Sprint sprint=sprintService.findSprintByNomSprint(nomSprint);
        return new ResponseEntity<>(sprint,OK);

    }
    @GetMapping("/findbyid/{idSprint}")
    public  ResponseEntity<Sprint> findSprintByIdSprint(@PathVariable("idSprint") long idSprint) {
        Sprint sprint=sprintService.findSprintByIdSprint(idSprint);
        return new ResponseEntity<>(sprint,OK);

    }



    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }


}

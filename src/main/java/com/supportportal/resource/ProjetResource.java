package com.supportportal.resource;


import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.Projet;
import com.supportportal.domain.User;
import com.supportportal.enumeration.ETAT;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.exception.projetException.ProjetNameExistException;
import com.supportportal.exception.projetException.ProjetNotFoundException;
import com.supportportal.service.ProjetService;
import com.supportportal.service.UserService;
import com.supportportal.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import  java.lang.*;

import java.util.Date;
import java.util.List;

import static com.supportportal.constant.ProjetImplConstant.PROJECTNAME_ALREADY_EXISTS;
import static com.supportportal.constant.UserImplConstant.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/projet"})
public class ProjetResource {

    private ProjetService projetService ;
    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

    private UserService userService;
    public static final String PROJECT_DELETED_SUCCESSFULLY = "Project deleted successfully";


    @Autowired
    public ProjetResource(ProjetService projetService,UserService userService) {
        this.projetService = projetService;
        this.userService=userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Projet> addNewProjet(@RequestBody Projet newprojet) throws ProjetNameExistException, ProjetNotFoundException {

    User u=newprojet.getCreePar();

       // User user= userService.findUserByUserId(u.getUserId());
       // LOGGER.info(user.getUsername()); //cree

      //  newprojet.setCreePar(user);
        LOGGER.info(u.getUsername()+"ressource"+newprojet.getCreePar().getUsername()); //cree
        LOGGER.info("|||etat projet |||"+newprojet.getEtatProjet());
        LOGGER.info("|||date |||"+newprojet.getDateEcheance());
        LOGGER.info(",,,,,,"+getEtatEnumName(newprojet.getEtatProjet()).name());


        Projet addedProjet=projetService.addNewProjet(newprojet.getIdProjet(),newprojet.getNameProjet(),newprojet.getEtatProjet(),newprojet.getCreePar(),newprojet.getClient(),newprojet.getDateEcheance());
        return new ResponseEntity<>(addedProjet,OK);

    }

    private ETAT getEtatEnumName(String etat) {
        return ETAT.valueOf(etat.toUpperCase());
    }



   @PutMapping("/update/{idProjet}")
    public ResponseEntity<Projet> updateProjet(@PathVariable long idProjet,  @RequestBody Projet updateProjet) throws ProjetNameExistException, ProjetNotFoundException {
       Projet testingProj=this.projetService.findProjetByIdProjet(idProjet);
      //  ValidateNewnomProjet(updateProjet.getNameProjet(),testingProj.getNameProjet());

       updateProjet.setIdProjet(idProjet);
       updateProjet.setDateModification(new Date());
LOGGER.info(",,,,,,update "+updateProjet.getCreePar().getUsername());
      Projet updatedProjet=projetService.updateProjet(testingProj.getNameProjet(),updateProjet.getIdProjet(),updateProjet.getNameProjet(),updateProjet.getEtatProjet(),updateProjet.getCreePar(),updateProjet.getClient(),updateProjet.getDateCreation(),updateProjet.getDateEcheance(),updateProjet.getDateModification());
      updatedProjet.setIdProjet(idProjet);

       return new ResponseEntity<>(updatedProjet,OK);

    }

    /*private Projet ValidateNewnomProjet( String nomProjet,String currentNomProjet ) throws ProjetNameExistException,ProjetNotFoundException{

        Projet projetByNewNomProjet=this.projetService.findProjetByNameProjet(nomProjet);
            if(StringUtils.isNotBlank(currentNomProjet)) {
                Projet currentProjet = this.projetService.findProjetByNameProjet(currentNomProjet);
                if(currentProjet == null) {
                    throw new ProjetNotFoundException("NO_Projet_FOUND_BY_Name" + currentProjet);
                }
                Long idCurrent=currentProjet.getIdProjet();
               // Long idNew=projetByNewNomProjet.getIdProjet();
                    if (projetByNewNomProjet != null && !currentProjet.equals(projetByNewNomProjet.getIdProjet()))
                        throw new ProjetNameExistException(PROJECTNAME_ALREADY_EXISTS); //test nom et id (a voir)

                return currentProjet;
            } else {
                if(projetByNewNomProjet != null) {
                    throw new ProjetNameExistException(PROJECTNAME_ALREADY_EXISTS ); //test nom et id (a voir)
                }

                return null;
            }

    }*/






  /*  @GetMapping("/Auth/{nomUser}")
    public List<Projet> ProjetByUser(@PathVariable("nomUser") String nomUser) {

        return projetService.findProjetByUser(userService.findUserByNomUser(nomUser));
    } */




    @GetMapping("/allProjets")
    //@PreAuthorize("hasAnyAuthority('projet:read')")
    public ResponseEntity<List<Projet>> getProjets(){
        List<Projet> projets= projetService.getProjets();
        return new ResponseEntity<>(projets,OK);

    }
    @DeleteMapping("/delete/{idProjet}")
     @PreAuthorize("hasAnyAuthority('projet:delete')")
    public ResponseEntity<HttpResponse> deleteProjet(@PathVariable("idProjet") long  idProjet){
        projetService.deleteProjet(idProjet);
        return response(NO_CONTENT, PROJECT_DELETED_SUCCESSFULLY);

    }

//    @GetMapping("/findSprint/{nomProjet}")
//    public ResponseEntity<List<Sprint>> getProjetSprint(@PathVariable("nomProjet")String nomProjet){
//        Projet projet=projetService.findProjetByNomProjet(nomProjet);
//        List<Sprint> sprints =projet.getSprints();
//        return new ResponseEntity<>(sprints,OK);
//    }




    @GetMapping("/findProjetbyname/{nameProjet}")
    public  ResponseEntity<Projet> findProjetByNameProjet(@PathVariable("nameProjet") String nameProjet) {
        Projet projet=projetService.findProjetByNameProjet(nameProjet);
        return new ResponseEntity<>(projet,OK);

    }
    @GetMapping("/findProjetbyid/{idProjet}")
    public  ResponseEntity<Projet> findProjetByIdProjet(@PathVariable("idProjet") long idProjet) {
        Projet projet=projetService.findProjetByIdProjet(idProjet);
        return new ResponseEntity<>(projet,OK);

    }


  /*@GetMapping("/findbycreateur/{username}")
    public ResponseEntity<List<Projet>> findProjetByCreePar(@PathVariable("username") String username ){
        User user=userService.findUserByUsername(username);
        LOGGER.info("name "+username);
       List< Projet> projets =user.getProjets();
              // projetService.findProjetByCreePar(user);
       // LOGGER.info("nomProjet"+ projets.getNameProjet());
        return new ResponseEntity<>(projets,OK);
    }*/


    @GetMapping("/active")
    public int getTotalProjetActive() {
        return this.projetService.getTotalProjetActive();
    }
    @GetMapping("/paused")
    public int getTotalProjetPaused() {
        return this.projetService.getTotalProjetPaused() ;
    }
    @GetMapping("/suspended")
    public int getTotalProjetSuspended() {
        return this.projetService.getTotalProjetSuspended();
    }
    @GetMapping("/Completed")
    public int getTotalProjetCompleted() {
        return this.projetService.getTotalProjetCompleted();
    }
    @GetMapping("/totalProjet")
    public int totalProjet() {
        return this.projetService.totalProjet();
    }




    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }





}

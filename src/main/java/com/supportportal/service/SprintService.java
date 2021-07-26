package com.supportportal.service;

import com.supportportal.domain.Projet;
import com.supportportal.domain.Sprint;
import com.supportportal.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SprintService {
    Sprint findSprintByNomSprint(String nomSprint) ;
    Sprint findSprintByIdSprint(long idSprint) ;
    Sprint findSprintByProjet(Projet projet);

    List<Sprint> getSprints();

    void deleteSprint(long idSprint);
   // Sprint addNewSprint(Sprint Newsprint) ;



Sprint addNewSprint(long idSprint, String nomSprint, Date dateFin,
                        String description, String etatSprint, Projet projet,User sprintCreePar,User chefAffecter) ;

Sprint updateSprint(long idSprint, String nomSprint, Date dateCreation, Date dateFin,
                        String description, String etatSprint, Projet projet,User sprintCreePar,User chefAffecter);





}

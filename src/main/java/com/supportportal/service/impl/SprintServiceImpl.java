package com.supportportal.service.impl;

import com.supportportal.domain.*;
import com.supportportal.enumeration.ETAT;
import com.supportportal.repository.ProjetRepository;
import com.supportportal.repository.SprintRepository;
import com.supportportal.service.ProjetService;
import com.supportportal.service.SprintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SprintServiceImpl implements SprintService {

    private SprintRepository sprintRepository;
    private ProjetRepository projetRepository;

@Autowired
    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;

    }


    @Override
    public Sprint findSprintByNomSprint(String nomSprint) {
        return sprintRepository.findSprintByNomSprint(nomSprint);      }

    @Override
    public Sprint findSprintByIdSprint(long idSprint) {
        return sprintRepository.findSprintByIdSprint(idSprint);
    }

    @Override
    public List<Sprint> getSprints() {
        return sprintRepository.findAll();
    }

    @Override
    public void deleteSprint(long idSprint) {
        sprintRepository.deleteById(idSprint);

    }

    @Override
    public Sprint addNewSprint(long idSprint, String nomSprint, Date dateFin, String description, String etatSprint, Projet projet, User sprintCreePar, User chefAffecter) {
       // ValidateNameSprint(StringUtils.EMPTY, nomSprint);
       // ValidateNameSprint(projet.getNameProjet(),nomSprint);
Sprint newSprint = new Sprint();
newSprint.setDateFin(dateFin);
newSprint.setIdSprint(idSprint);
newSprint.setNomSprint(nomSprint);
newSprint.setEtatSprint(getEtatEnumName(etatSprint).name());
newSprint.setChefAffecter(chefAffecter);
newSprint.setSprintCreePar(sprintCreePar);
newSprint.setDescription(description);
newSprint.setProjet(projet);
newSprint.setDateCreation (new Date());

        return sprintRepository.save(newSprint);



    }

    @Override
    public Sprint updateSprint(long idSprint, String nomSprint, Date dateCreation, Date dateFin, String description, String etatSprint, Projet projet, User sprintCreePar, User chefAffecter){
     //  Sprint updatedSprint=findSprintByNomSprint(currentNomSprint);
       // ValidateNameSprint(currentNomSprint,nomSprint);

        Sprint updatedSprint = new Sprint();
        updatedSprint.setDateFin(dateFin);
        updatedSprint.setIdSprint(idSprint);
        updatedSprint.setNomSprint(nomSprint);
        updatedSprint.setEtatSprint(getEtatEnumName(etatSprint).name());
        updatedSprint.setChefAffecter(chefAffecter);
        updatedSprint.setSprintCreePar(sprintCreePar);
        updatedSprint.setDescription(description);
        updatedSprint.setProjet(projet);
        updatedSprint.setDateCreation (dateCreation);
updatedSprint.setDateModification(new Date());
        return sprintRepository.save(updatedSprint);


    }

    @Override
    public int getTotalSprintDevelopment() {
        return this.sprintRepository.getTotalSprintDevelopment();
    }

    @Override
    public int getTotalSprintDelivery() {
        return this.sprintRepository.getTotalSprintDelivery();
    }

    @Override
    public int getTotalSprintTesting() {
        return this.sprintRepository.getTotalSprintTesting();
    }

    @Override
    public int getTotalSprintPaused() {
        return this.sprintRepository.getTotalSprintPaused();
    }

    @Override
    public int totalSprint() {
        return this.sprintRepository.totalSprint();
    }

    @Override
    public int findTotalByProjetId(Projet projet) {
        return sprintRepository.findTotalByProjetId(projet.getIdProjet());
    }
    @Override
    public List<Sprint> findSprintByProjetId(Long idProjet){
    Projet findProjet = new Projet();
    findProjet=projetRepository.findProjetByIdProjet(idProjet);
        return  sprintRepository.findSprintByProjet(findProjet);}



  /*  private Sprint ValidateNameSprint(String currentNomSprint,String nomSprint ) throws SprintNameExistException {

        Sprint sprintByNewNomSprint=findSprintByNomSprint(nomSprint);

        if(StringUtils.isNotBlank(currentNomSprint)) {
            Sprint currentSprint = findSprintByNomSprint(currentNomSprint);
            if(currentSprint == null) {
                throw new SprintNameExistException("NO_Projet_FOUND_BY_Name" + currentSprint);//on vera
            }
            Long idCurrent=currentSprint.getIdSprint();
            Projet currentProjet=currentSprint.getProjet();
            LOGGER.info(currentProjet.getNameProjet());
            // Long idNew=projetByNewNomProjet.getIdProjet();
LOGGER.info (String.valueOf(currentProjet.getNameProjet().equals(sprintByNewNomSprint.getProjet().getNameProjet())));
            if (sprintByNewNomSprint != null && currentProjet.getNameProjet().equals(sprintByNewNomSprint.getProjet().getNameProjet()) )
                throw new SprintNameExistException("SprintNAME_ALREADY_EXISTS-Into thisProject "); //test nom et id (a voir)

            return currentSprint;
        } else {
            Sprint tousSprints= (Sprint) getSprints();
            LOGGER.info(tousSprints.getProjet().getNameProjet());
            if(sprintByNewNomSprint != null &&(sprintByNewNomSprint.getProjet().getNameProjet().equals(tousSprints.getProjet().getNameProjet()) ) ){
                throw new SprintNameExistException("SprintNAME_ALREADY_EXISTS" ); //test nom et id (a voir)
            }

            return null;
        }

    }*/




    private ETAT getEtatEnumName(String etat) {
        return ETAT.valueOf(etat.toUpperCase());
    }
}

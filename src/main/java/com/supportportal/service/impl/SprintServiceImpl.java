package com.supportportal.service.impl;

import com.supportportal.domain.Projet;
import com.supportportal.domain.Sprint;
import com.supportportal.domain.User;
import com.supportportal.enumeration.ETAT;
import com.supportportal.repository.SprintRepository;
import com.supportportal.service.SprintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SprintServiceImpl implements SprintService {

    private SprintRepository sprintRepository;

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
       // ValidateNewnomProjet( nameProjet,creePar.getUsername());

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
    public Sprint updateSprint(long idSprint, String nomSprint, Date dateCreation, Date dateFin, String description, String etatSprint, Projet projet, User sprintCreePar, User chefAffecter) {
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


    private ETAT getEtatEnumName(String etat) {
        return ETAT.valueOf(etat.toUpperCase());
    }
}

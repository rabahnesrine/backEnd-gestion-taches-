package com.supportportal.service.impl;

import com.supportportal.domain.Projet;
import com.supportportal.domain.User;
import com.supportportal.enumeration.ETAT;
import com.supportportal.exception.projetException.ProjetNameExistException;
import com.supportportal.exception.projetException.ProjetNotFoundException;
import com.supportportal.repository.ProjetRepository;
import com.supportportal.resource.UserResource;
import com.supportportal.service.ProjetService;
import com.supportportal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.supportportal.constant.ProjetImplConstant.PROJECTNAME_ALREADY_EXISTS;

@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {
    private ProjetRepository projetRepository;
    private UserResource userResource;
    private UserService userService;
    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);




    @Autowired
    public ProjetServiceImpl(ProjetRepository projetRepository,UserResource userResource) {
        this.projetRepository = projetRepository;
        this.userResource=userResource;
    }


    @Override
    public List<Projet> getProjets()
    {
        return projetRepository.findAll();
    }
    @Override
    public void deleteProjet(long idProjet) {
        projetRepository.deleteById(idProjet);

    }

    @Override
    public Projet addNewProjet(Long idProjet,String nameProjet,String etatProjet ,User creePar,User client, Date dateEcheance) throws ProjetNameExistException, ProjetNotFoundException {
      //  ValidateNewnomProjet(nameProjet);
       ValidateUpdateProjet(nameProjet,StringUtils.EMPTY);

       Projet newprojet= new Projet() ;
    newprojet.setDateEcheance(dateEcheance);
         newprojet.setCreePar(creePar);
         newprojet.setClient(client);
    newprojet.setIdProjet(idProjet);
    newprojet.setNameProjet(nameProjet);
        newprojet.setEtatProjet(getEtatEnumName(etatProjet).name());
        LOGGER.info(",,,,,,"+getEtatEnumName(etatProjet).name());
/* if (etatProjet==null){
    newprojet.setEtatProjet("Non Commencer");
}
else
{newprojet.setEtatProjet(etatProjet);}
*/



        newprojet.setDateCreation (new Date());

          return projetRepository.save(newprojet);

    }


    private Projet ValidateNewnomProjet( String nomProjet ) throws ProjetNameExistException{
        Projet newProjet=findProjetByNameProjet(nomProjet);



        if(newProjet!=null ) {
               throw new ProjetNameExistException(PROJECTNAME_ALREADY_EXISTS ); //test nom et id (a voir)
            }

            return null ;

    }


    private Projet ValidateUpdateProjet( String nomProjet,String currentNomProjet ) throws ProjetNameExistException, ProjetNotFoundException {

        Projet projetByNewNomProjet=findProjetByNameProjet(nomProjet);
        if(StringUtils.isNotBlank(currentNomProjet)) {
            Projet currentProjet = findProjetByNameProjet(currentNomProjet);
            if(currentProjet == null) {
                throw new ProjetNotFoundException("NO_Projet_FOUND_BY_Name" + currentProjet);
            }
            Long idCurrent=currentProjet.getIdProjet();
            // Long idNew=projetByNewNomProjet.getIdProjet();
            if (projetByNewNomProjet != null && !idCurrent.equals(projetByNewNomProjet.getIdProjet()))
                throw new ProjetNameExistException(PROJECTNAME_ALREADY_EXISTS); //test nom et id (a voir)

            return currentProjet;
        } else {
            if(projetByNewNomProjet != null) {
                throw new ProjetNameExistException(PROJECTNAME_ALREADY_EXISTS ); //test nom et id (a voir)
            }

            return null;
        }

    }




    @Override
    public Projet updateProjet(String currentNom, Long idProjet,String nameProjet,String etatProjet ,User creePar,User client,Date dateCreation, Date dateEcheance,Date dateModification) throws ProjetNameExistException, ProjetNotFoundException {
        Projet newprojet=findProjetByNameProjet(currentNom);
        ValidateUpdateProjet(nameProjet,currentNom);
        newprojet.setDateEcheance(dateEcheance);
        newprojet.setCreePar(creePar);
        newprojet.setClient(client);
        newprojet.setDateCreation(dateCreation);
        newprojet.setIdProjet(idProjet);
        newprojet.setNameProjet(nameProjet);
        newprojet.setEtatProjet(getEtatEnumName(etatProjet).name());
        LOGGER.info(",,,,,,"+getEtatEnumName(etatProjet).name());
/* if (etatProjet==null){
    newprojet.setEtatProjet("Non Commencer");
}
else
{newprojet.setEtatProjet(etatProjet);}
*/

        newprojet.setDateModification(new Date());

        return projetRepository.save(newprojet);

    }



   /* @Override
    public Projet updateProjet(Long currentidProjet,Projet newProjet) {
        Projet currentProjet =projetRepository.findProjetByIdProjet(currentidProjet);
        System.err.println(newProjet.getIdProjet());


        currentProjet.setDateEcheance(newProjet.getDateEcheance());
        //date modification
        currentProjet.setCreePar(newProjet.getCreePar());
        currentProjet.setNameProjet(newProjet.getNameProjet());
        currentProjet.setEtatProjet(getEtatEnumName(newProjet.getEtatProjet()).name());
        LOGGER.info(",,,,,,"+getEtatEnumName(newProjet.getEtatProjet()).name());
        //currentProjet.setDateCreation (new Date());

         projetRepository.save(currentProjet);
         return  currentProjet;

    }*/

    private ETAT getEtatEnumName(String etat) {
        return ETAT.valueOf(etat.toUpperCase());
    }

    @Override
    public Projet findProjetByNameProjet(String nomProjet) {
        return projetRepository.findProjetByNameProjet(nomProjet);    }

    @Override
    public Projet findProjetByIdProjet(long idProjet) {
        return projetRepository.findProjetByIdProjet(idProjet);    }

    @Override
    public Projet findProjetByCreePar(User creePar){
        return  projetRepository.findProjetByCreePar(creePar);}

    @Override
    public int getTotalProjetActive() {
        return this.projetRepository.getTotalProjetActive();
    }

    @Override
    public int getTotalProjetPaused() {
        return this.projetRepository.getTotalProjetPaused();
    }

    @Override
    public int getTotalProjetSuspended() {
        return this.projetRepository.getTotalProjetSuspended();
    }

    @Override
    public int getTotalProjetCompleted() {
        return this.projetRepository.getTotalProjetCompleted();
    }

    @Override
    public int totalProjet() {
        return this.projetRepository.totalProjet();
    }
}






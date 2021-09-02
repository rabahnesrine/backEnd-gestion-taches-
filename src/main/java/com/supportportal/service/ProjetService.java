package com.supportportal.service;

import com.supportportal.domain.Projet;
import com.supportportal.domain.User;
import com.supportportal.exception.projetException.ProjetNameExistException;
import com.supportportal.exception.projetException.ProjetNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ProjetService {


    List<Projet> getProjets() ;
    void deleteProjet(long idProjet) ;
  //  Projet addNewProjet(Projet newprojet) ;
    Projet addNewProjet(Long idProjet, String nameProjet, String etatProjet , User creePar,User client , Date dateEcheance) throws ProjetNameExistException, ProjetNotFoundException;
  //  Projet updateProjet(Long currentidProjet,Projet newProjet) ;

    Projet updateProjet(String currentNom,Long idProjet, String nameProjet, String etatProjet , User creePar,User client ,Date dateCreation, Date dateEcheance,Date dateModification) throws ProjetNameExistException, ProjetNotFoundException;



    Projet findProjetByNameProjet(String nameProjet);
    Projet findProjetByIdProjet(long idProjet);
    Projet findProjetByCreePar(User creePar);


    int getTotalProjetActive();
    int getTotalProjetPaused();
    int getTotalProjetSuspended();
    int getTotalProjetCompleted();
    int totalProjet();

//  List<Projet> findProjetByUser(User user) ;
//Projet addNewMember(Projet projet,User user);
    //Projet addNewProjet(String nomProjet, Date dateEcheance,User creePar) throws ProjetNotFoundException, ProjetNameExistException, UserProjetExistException;

}

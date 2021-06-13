package com.supportportal.service;

import com.supportportal.domain.Projet;
import com.supportportal.domain.User;
import com.supportportal.exception.projetException.ProjetNameExistException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ProjetService {


    List<Projet> getProjets() ;
    void deleteProjet(long idProjet) ;
  //  Projet addNewProjet(Projet newprojet) ;
    Projet addNewProjet(Long idProjet, String nameProjet, String etatProjet , User creePar , Date dateEcheance) throws ProjetNameExistException;
  //  Projet updateProjet(Long currentidProjet,Projet newProjet) ;

    Projet updateProjet(Long idProjet, String nameProjet, String etatProjet , User creePar ,Date dateCreation, Date dateEcheance,Date dateModification) ;



    Projet findProjetByNameProjet(String nameProjet);
    Projet findProjetByIdProjet(long idProjet);
    Projet findProjetByCreePar(User creePar);




//  List<Projet> findProjetByUser(User user) ;
//Projet addNewMember(Projet projet,User user);
    //Projet addNewProjet(String nomProjet, Date dateEcheance,User creePar) throws ProjetNotFoundException, ProjetNameExistException, UserProjetExistException;

}

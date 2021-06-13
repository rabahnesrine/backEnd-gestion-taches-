package com.supportportal.repository;

import com.supportportal.domain.Projet;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet,Long> {

    public Projet findProjetByNameProjet(String nameProjet);
    public Projet findProjetByIdProjet(long idProjet);


    public Projet findProjetByCreePar(User creePar);

}

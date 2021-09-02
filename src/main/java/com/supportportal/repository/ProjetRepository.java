package com.supportportal.repository;

import com.supportportal.domain.Projet;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjetRepository extends JpaRepository<Projet,Long> {

    public Projet findProjetByNameProjet(String nameProjet);
    public Projet findProjetByIdProjet(long idProjet);


    public Projet findProjetByCreePar(User creePar);

    @Query("SELECT COUNT(t) FROM Projet t ")
    int totalProjet();

    @Query("SELECT COUNT(t.etatProjet) FROM Projet t WHERE t.etatProjet ='ETAT_ACTIVE'")
    int getTotalProjetActive();

    @Query("SELECT COUNT(t.etatProjet) FROM Projet t WHERE t.etatProjet ='ETAT_SUSPENDED'")
    int getTotalProjetSuspended();

    @Query("SELECT COUNT(t.etatProjet) FROM Projet t WHERE t.etatProjet ='ETAT_COMPLETED'")
    int getTotalProjetCompleted();

    @Query("SELECT COUNT(t.etatProjet) FROM Projet t WHERE t.etatProjet ='ETAT_PAUSED'")
    int getTotalProjetPaused();

}

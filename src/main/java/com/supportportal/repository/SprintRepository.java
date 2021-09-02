package com.supportportal.repository;

import com.supportportal.domain.Attachement;
import com.supportportal.domain.Projet;
import com.supportportal.domain.Sprint;
import com.supportportal.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint,Long> {
    public Sprint findSprintByNomSprint(String nomSprint);
    public Sprint findSprintByIdSprint(long idSprint);
    //public Sprint findSprintByProjet(Projet projet);
    List<Sprint> findSprintByProjet(Projet projet);

    //  nb of sprint by projet id

    @Query("SELECT COUNT(t) FROM Sprint t WHERE t.projet.idProjet=:idProjet")
    int findTotalByProjetId(@Param("idProjet") Long idProjet);


    @Query("SELECT COUNT(t) FROM Sprint t ")
    int totalSprint();

    @Query("SELECT COUNT(t.etatSprint) FROM Sprint t WHERE t.etatSprint ='ETAT_PAUSED'")
    int getTotalSprintPaused();

    @Query("SELECT COUNT(t.etatSprint) FROM Sprint t WHERE t.etatSprint ='ETAT_TESTING'")
    int getTotalSprintTesting();

    @Query("SELECT COUNT(t.etatSprint) FROM Sprint t WHERE t.etatSprint ='ETAT_DELIVERY'")
    int getTotalSprintDelivery();

    @Query("SELECT COUNT(t.etatSprint) FROM Sprint t WHERE t.etatSprint ='ETAT_DEVELOPMENT'")
    int getTotalSprintDevelopment();

}

package com.supportportal.domain;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Projet implements Serializable {


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private  long idProjet;
    private String nameProjet ;
    @Temporal(TemporalType.DATE)
	private Date dateModification ;

    @Temporal(TemporalType.DATE)
    private Date dateCreation ;

    @Temporal(TemporalType.DATE)
    private  Date dateEcheance;
    private String etatProjet;

   @OneToMany( mappedBy = "projet",cascade = CascadeType.ALL)
    private List<Sprint> sprints;

      /*  private boolean archiveProjet ;

*/
    @ManyToOne(cascade = CascadeType.REFRESH)
    private User creePar;
    @ManyToOne
    private User client;

    public Projet(long idProjet, String nameProjet, Date dateCreation, Date dateEcheance,Date dateModification, String etatProjet, User creePar,List<Sprint> sprints,User client
                  ) {
        super();
        this.idProjet = idProjet;
        this.nameProjet = nameProjet;
        this.dateCreation = dateCreation;
        this.dateEcheance = dateEcheance;
        this.etatProjet = etatProjet;
        this.dateModification = dateModification;
        this.creePar=creePar;
        this.sprints = sprints;
        this.client=client;




    }
    public Projet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public List<Sprint> getSprints() {
        return null;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public long getIdProjet() {
        return this.idProjet;
    }
    public void setIdProjet(long idProjet) {
        this.idProjet = idProjet;
    }
    public String getNameProjet() {
        return this.nameProjet;
    }
    public void setNameProjet(String nomProjet) {
        this.nameProjet = nomProjet;
    }
    public Date getDateCreation() {
        return this.dateCreation;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    public Date getDateEcheance() {
        return this.dateEcheance;
    }
    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }
    public String getEtatProjet() {
        return this.etatProjet;
    }
    public void setEtatProjet(String etatProjet) {
        this.etatProjet = etatProjet;
    }

    public Date getDateModification() {
        return this.dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public User getCreePar() {
        return this.creePar;
    }
    public void setCreePar(User creePar) {
        this.creePar = creePar;
    }

    public User getClient() {
        return this.client;
    }

    public void setClient(User client) {
        this.client = client;
    }

/*
    public boolean isArchiveProjet() {
        return archiveProjet;
    }
    public void setArchiveProjet(boolean archiveProjet) {
        this.archiveProjet = archiveProjet;
    }

  public List<Sprint> getSprints() {
        return null;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }*/
//@ManyToOne(fetch = FetchType.EAGER)
    // private User []usersInclus ;


}

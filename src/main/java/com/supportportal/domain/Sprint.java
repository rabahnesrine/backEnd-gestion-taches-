package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Sprint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idSprint;
    private String nomSprint;
    @Temporal(TemporalType.DATE)
    private Date dateCreation ;
    @Temporal(TemporalType.DATE)
    private Date  dateModification;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private String description;
    private String etatSprint;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private User sprintCreePar;//recupere user a partir instance projet

    //    @ManyToOne
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Projet projet ;
     @ManyToOne
  private  User chefAffecter;


    @OneToMany( mappedBy = "sprint",cascade = CascadeType.ALL)
    private List<Task> tasks;

    public List<Task> getTasks() {
        return null;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
//
//  @OneToMany(cascade=CascadeType.ALL,mappedBy = "sprint")
//  private List<Tache> taches;


    public User getChefAffecter() {
        return this.chefAffecter;
    }

    public void setChefAffecter(User chefAffecter) {
        this.chefAffecter = chefAffecter;
    }

    public long getIdSprint() {
        return idSprint;
    }
    public void setIdSprint(long idSprint) {
        this.idSprint = idSprint;
    }
    public String getNomSprint() {
        return nomSprint;
    }
    public void setNomSprint(String nomSprint) {
        this.nomSprint = nomSprint;
    }
    public Date getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    public Date getDateModification() {
        return dateModification;
    }
    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
    public Date getDateFin() {
        return dateFin;
    }
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEtatSprint() {
        return etatSprint;
    }
    public void setEtatSprint(String etatSprint) {
        this.etatSprint = etatSprint;
    }

    public User getSprintCreePar() {
        return sprintCreePar;
    }

    public void setSprintCreePar(User sprintCreePar) {
        this.sprintCreePar = sprintCreePar;
    }


    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Sprint(long idSprint, String nomSprint, Date dateCreation, Date dateModification, Date dateFin,
                  String description, String etatSprint, Projet projet,User sprintCreePar,User chefAffecter ,List<Task> tasks) {
        super();
        this.idSprint = idSprint;
        this.nomSprint = nomSprint;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.dateFin = dateFin;
        this.description = description;
        this.etatSprint = etatSprint;
        this.projet = projet;
        this.sprintCreePar=sprintCreePar;
        this.chefAffecter=chefAffecter;
        this.tasks=tasks;

    }
    public Sprint() {
        super();
        // TODO Auto-generated constructor stub
    }


}





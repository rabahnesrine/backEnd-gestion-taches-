package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idTask;
    private String nameTask;
    @Temporal(TemporalType.DATE)
    private Date dateCreation ;
    @Temporal(TemporalType.DATE)
    private Date  dateModification;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private String description;
    private String etatTask;
    @ManyToOne
    private User taskCreePar;
    @ManyToOne
    private Sprint sprint ;
    @ManyToOne
    private  User memberAffecter;
    private boolean archive;


    @OneToMany( mappedBy = "taskAtt")
    private List<Attachement> attachements;

    @OneToMany( mappedBy = "tacheCom")
    private List<Commentaire> commentaires;


    public long getIdTask() {
        return this.idTask;
    }

    public void setIdTask(long idTask) {
        this.idTask = idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
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

    public String getEtatTask() {
        return etatTask;
    }

    public void setEtatTask(String etatTask) {
        this.etatTask = etatTask;
    }

    public User getTaskCreePar() {
        return taskCreePar;
    }

    public void setTaskCreePar(User taskCreePar) {
        this.taskCreePar = taskCreePar;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public User getMemberAffecter() {
        return memberAffecter;
    }

    public void setMemberAffecter(User memberAffecter) {
       this.memberAffecter = memberAffecter;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public List<Attachement> getAttachements() {
        return null;
    }

    public void setAttachements(List<Attachement> attachements) {
        this.attachements = attachements;
    }

    public List<Commentaire> getCommentaires() {
        return null;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }
    public Task() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Task(long idTask, String nameTask, Date dateCreation, Date dateModification, Date dateFin, String description, String etatTask, User taskCreePar, Sprint sprint, User memberAffecter, boolean archive, List<Attachement> attachements, List<Commentaire> commentaires) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.dateFin = dateFin;
        this.description = description;
        this.etatTask = etatTask;
        this.taskCreePar = taskCreePar;
        this.sprint = sprint;
        this.memberAffecter = memberAffecter;
        this.archive = archive;
        this.attachements = attachements;
        this.commentaires = commentaires;
    }


}

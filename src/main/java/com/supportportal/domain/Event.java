package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String subject;
    @Column(length = 200)
    private String description;
    @ElementCollection(targetClass = Long.class)
    private List<Long> invitedPersons;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private User eventUser;
    private boolean archive;
    private String etatEvent;
    private Date dateCreation;
    private Date dateModification;
    private String timeEvent;
    private String categorie;
    private String lieu;


    public Event() {
    }

    public Event(long id, String subject, String description, List<Long> invitedPersons, Date startDate, Date endDate, User eventUser, boolean archive, String etatEvent, Date dateCreation, Date dateModification,String timeEvent, String categorie, String lieu) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.invitedPersons = invitedPersons;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventUser = eventUser;
        this.archive = archive;
        this.etatEvent = etatEvent;
        this.dateCreation = dateCreation;
        this.dateModification=dateModification;
        this.timeEvent = timeEvent;
        this.categorie = categorie;
        this.lieu = lieu;
    }

    public Date getDateModification() {
        return this.dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getEventUser() {
        return this.eventUser;
    }

    public void setEventUser(User eventUser) {
        this.eventUser = eventUser;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String getEtatEvent() {
        return this.etatEvent;
    }

    public void setEtatEvent(String etatEvent) {
        this.etatEvent = etatEvent;
    }

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTimeEvent() {
        return this.timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getLieu() {
        return this.lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long idEvent) {
        this.id = idEvent;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String title) {
        this.subject = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getInvitedPersons() {
        return this.invitedPersons;
    }

    public void setInvitedPersons(List<Long> invitedPersons) {
        this.invitedPersons = invitedPersons;
    }
}
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
    private String description;
    @ElementCollection(targetClass=Long.class)
    private List<Long> invitedPersons;
    private Date startTime;
    private Date endTime;

    public Event() { }
    public Event(long idEvent, String title, String description, List<Long> invitedPersons, Date startDate, Date endDate) {
this.id=idEvent;
        this.subject = title;
        this.description = description;
        this.invitedPersons = invitedPersons;
        this.startTime = startDate;
        this.endTime = endDate;
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

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startDate) {
        this.startTime = startDate;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endDate) {
        this.endTime = endDate;
    }
}

package com.supportportal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity

public class Video implements Serializable {


    @Id
    @GeneratedValue
    private long id;
    private String nomVedio;

    @Column(length = 200)
    private String description;
    private String file;
    private Date dateCreation;
    private String authorisation;

    public Video(){}
    public Video(long id, String nomVedio, String authorisation, String description, String file, Date dateCreation) {
        this.id = id;
        this.nomVedio=nomVedio;
        this.description=description;
        this.file=file;
        this.dateCreation=dateCreation;
        this.authorisation=authorisation;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomVedio() {
        return nomVedio;
    }

    public void setNomVedio(String nomVedio) {
        this.nomVedio = nomVedio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getAuthorisation() {
        return authorisation;
    }

    public void setAuthorisation(String authorisation) {
        this.authorisation = authorisation;
    }
}

package com.supportportal.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Document  implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String nomDocument;
    private String typeDocument;
    @Column(length = 200)
    private String description;
    private String file;
    private Date dateCreation;
    private boolean etat;
    private boolean archive;


    @ManyToOne(cascade = CascadeType.REFRESH)
    private User docUser;

public  Document(){}
    public Document(long id, String nomDocument, String typeDocument, String description, String file, Date dateCreation, boolean etat, boolean archive, User docUser) {
        this.id = id;
        this.nomDocument = nomDocument;
        this.typeDocument = typeDocument;
        this.description = description;
        this.file = file;
        this.dateCreation = dateCreation;
        this.etat = etat;
        this.archive = archive;
        this.docUser = docUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomDocument() {
        return nomDocument;
    }

    public void setNomDocument(String nomDocument) {
        this.nomDocument = nomDocument;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
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

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public User getDocUser() {
        return docUser;
    }

    public void setDocUser(User docUser) {
        this.docUser = docUser;
    }
}

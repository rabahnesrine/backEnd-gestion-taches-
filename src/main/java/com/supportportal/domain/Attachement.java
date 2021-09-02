package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class Attachement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAttachement;

    private String file;
    @Temporal(TemporalType.DATE)

    private Date dateCreation;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Task taskAtt;

    public long getIdAttachement() {
        return idAttachement;
    }

    public void setIdAttachement(long idAttachement) {
        this.idAttachement = idAttachement;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Task getTaskAtt() {
        return this.taskAtt;
    }

    public void setTaskAtt(Task tacheAtt) {
        this.taskAtt = tacheAtt;
    }

    public Attachement(long idAttachement, String file, Date dateCreation, Task tacheAtt) {
        this.idAttachement = idAttachement;
        this.file = file;
        this.dateCreation = dateCreation;
        this.taskAtt = tacheAtt;
    }
    public Attachement() {
        super();
        // TODO Auto-generated constructor stub
    }
}

package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class Commentaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCommentaire;


    @Column(length=250)
    private String comment;

    @Temporal(TemporalType.DATE)
    private Date dateComment;

    @ManyToOne
    private Task tacheCom;
    @ManyToOne
    private User userCom;

    public long getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(long idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public Task getTacheCom() {
        return this.tacheCom;
    }

    public void setTacheCom(Task tacheCom) {
        this.tacheCom = tacheCom;
    }

    public User getUserCom() {
        return userCom;
    }

    public void setUserCom(User userCom) {
        this.userCom = userCom;
    }

    public Commentaire(long idCommentaire, String comment, Date dateComment, Task tacheCom, User userCom) {
        this.idCommentaire = idCommentaire;
        this.comment = comment;
        this.dateComment = dateComment;
        this.tacheCom = tacheCom;
        this.userCom = userCom;
    }

    public Commentaire() {
        super();
        // TODO Auto-generated constructor stub
    }
}

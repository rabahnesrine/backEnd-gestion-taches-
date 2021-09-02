package com.supportportal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity

public class Msg implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idMsg;
    private Date dateCreation ;


    private String msg;
    private boolean vu;
    private String roomName;

    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;


public  Msg(){}
    public Msg(long idMsg, Date dateCreation, String msg, boolean vu, String roomName, User sender,User receiver) {
        this.idMsg = idMsg;
        this.dateCreation = dateCreation;
        this.msg = msg;
        this.vu = vu;
        this.roomName = roomName;
        this.sender = sender;
        this.receiver=receiver;
    }

    public User getReceiver() {
        return this.receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public long getIdMsg() {
        return this.idMsg;
    }

    public void setIdMsg(long idMsg) {
        this.idMsg = idMsg;
    }



    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isVu() {
        return this.vu;
    }

    public void setVu(boolean vu) {
        this.vu = vu;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public User getSender() {
        return this.sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }


}

package com.supportportal.service;

import com.supportportal.domain.Attachement;
import com.supportportal.domain.Msg;
import com.supportportal.domain.User;
import org.springframework.stereotype.Service;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Service
public interface MsgService {


    void deleteMsg(long idMsg);

    Msg sendMsg(long idMsg, String msg, boolean vu, String roomName, User sender, User receiver) ;

    List<Msg> findMsgByIdSender(Long id);
    List<Msg> findMsgByIdReceiver(Long id);
    List<Msg>findMsgByIdSenderAndIdReceiver(Long idSender,Long idReceiver);
    List<Msg> findAllMsg();
     Msg updateMsg(long idMsg);



    }

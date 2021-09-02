package com.supportportal.repository;

import com.supportportal.domain.Msg;
import com.supportportal.domain.Task;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsgRepository extends JpaRepository<Msg,Long> {
    List<Msg> findMsgBySender(User sender);
    List<Msg> findMsgByReceiver(User receiver);
    List<Msg>findMsgBySenderAndReceiver(User sender,User receiver);
    Msg findMsgByIdMsg(long idMsg);



}

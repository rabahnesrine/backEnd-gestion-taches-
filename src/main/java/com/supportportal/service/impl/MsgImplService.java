package com.supportportal.service.impl;

import com.supportportal.domain.Msg;
import com.supportportal.domain.User;
import com.supportportal.repository.MsgRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MsgImplService implements MsgService {

    private MsgRepository msgRepository;
    private UserRepository userRepository;

@Autowired
    public MsgImplService(MsgRepository msgRepository,UserRepository userRepository) {
    this.msgRepository=msgRepository;
    this.userRepository=userRepository;
    }

    @Override
    public void deleteMsg(long idMsg) {
    this.msgRepository.deleteById(idMsg);

    }

    @Override
    public Msg updateMsg(long idMsg) {
    Msg msgVu = new Msg();
      msgVu =  this.msgRepository.findMsgByIdMsg(idMsg);
      msgVu.setVu(true);
      return msgRepository.save(msgVu);
    }

    @Override
    public Msg sendMsg(long idMsg, String msg, boolean vu, String roomName, User sender, User receiver) {
      Msg sendMsg =new Msg();
      sendMsg.setIdMsg(idMsg);
      sendMsg.setMsg(msg);
      sendMsg.setVu(vu);
      sendMsg.setRoomName(roomName);
      sendMsg.setSender(sender);
      sendMsg.setReceiver(receiver);
      sendMsg.setDateCreation (new Date());

        return msgRepository.save(sendMsg);
    }

    @Override
    public List<Msg> findMsgByIdSender(Long id) {
    User sender =new User();
    sender=this.userRepository.findUserById(id);
        return msgRepository.findMsgBySender(sender);
    }

    @Override
    public List<Msg> findMsgByIdSenderAndIdReceiver(Long idSender, Long idReceiver) {
        User receiver =new User();
        User sender =new User();
        receiver=this.userRepository.findUserById(idReceiver);
        sender=this.userRepository.findUserById(idSender);
return msgRepository.findMsgBySenderAndReceiver(sender,receiver);

    }

    @Override
    public List<Msg> findMsgByIdReceiver(Long id) {
    User receiver =new User();
    receiver=this.userRepository.findUserById(id);
        return msgRepository.findMsgByReceiver(receiver);
    }

    @Override
    public List<Msg> findAllMsg() {
        return msgRepository.findAll();
    }
}

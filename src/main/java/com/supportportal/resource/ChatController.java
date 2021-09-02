package com.supportportal.resource;

import com.supportportal.domain.Chat;
import com.supportportal.repository.ChatRepository;
import com.supportportal.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path={"/msg"})
public class ChatController {

    @Autowired
    ChatService chatService;
@Autowired
ChatRepository chatRepository;
    @MessageMapping("/send/message/")
    @SendTo("/sendMsg/chatRoom")
    public void sendMessage(@Payload Chat message){
        this.chatService.post(message);
    }


    @GetMapping("get/start")
    public java.util.List<Chat> getFiveLatestChat(){
        return chatService.getFiveLatestChat();

    }
}

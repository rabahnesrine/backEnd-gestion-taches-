package com.supportportal.resource;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.Msg;
import com.supportportal.domain.Sprint;
import com.supportportal.domain.User;
import com.supportportal.service.MsgService;
import com.supportportal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/msg"})
public class MsgResource {

    private UserService userService;
    private MsgService msgService;

    public MsgResource(UserService userService, MsgService msgService) {
        this.userService = userService;
        this.msgService = msgService;
    }


    @PostMapping("/add")
    public ResponseEntity<Msg> sendMsg(@RequestBody Msg newMsg) {

        Msg addedMsg=msgService.sendMsg(newMsg.getIdMsg(),
                newMsg.getMsg(),newMsg.isVu() , newMsg.getRoomName(),newMsg.getSender(),
                newMsg.getReceiver());
        return new ResponseEntity<>(addedMsg,OK);

    }


    @PutMapping("/updateVu/{idMsg}")
    public ResponseEntity<Msg> vuMsg(@PathVariable long idMsg,@RequestBody Msg msg) {

        Msg updatedMsg=msgService.updateMsg(msg.getIdMsg());
        return new ResponseEntity<>(updatedMsg,OK);

    }



    @GetMapping("/all")
    public ResponseEntity<List<Msg>> getMsgs(){
        List<Msg> msgs= msgService.findAllMsg();
        return new ResponseEntity<>(msgs,OK);

    }


    @GetMapping("/findbySender/{idSender}/findbyReceiver/{idReceiver}")
    public  ResponseEntity<List<Msg>> findMsgBySenderAndReceiver(@PathVariable("idSender") long idSen ,@PathVariable("idReceiver") long idRec) {
        List<Msg> msgs= this.msgService.findMsgByIdSenderAndIdReceiver(idSen,idRec);
msgs.addAll(this.msgService.findMsgByIdSenderAndIdReceiver(idRec,idSen));
        return new ResponseEntity<>(msgs,OK);
    }

    @GetMapping("/findbySend/{idSender}/findbyRece/{idReceiver}")
    public  ResponseEntity<List<Msg>> findMsgBySendAndRece(@PathVariable("idSender") long idSen ,@PathVariable("idReceiver") long idRec) {
        List<Msg> msgs= this.msgService.findMsgByIdSenderAndIdReceiver(idSen,idRec);
        return new ResponseEntity<>(msgs,OK);
    }



    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }

}

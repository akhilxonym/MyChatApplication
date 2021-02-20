package org.mychat.backend.Rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.mychat.backend.model.Chat;
import org.mychat.backend.service.ChatService;


@RestController
@RequestMapping("chat")
public class ChatWebService {
    @Autowired
    private ChatService chatService;

    @RequestMapping("fetchChat")
    public List<Chat> getPersons(@RequestBody Map<String,Object> data){
        String from =(String)data.get("from");
        String to=(String)data.get("to");
        int skip=(int) data.get("skip");
        return chatService.fetchChat(from,to,skip);
    }

    @RequestMapping("saveChat")
    public void getPersons(@RequestBody Chat chat){
         chatService.saveChat(chat);
    }
}
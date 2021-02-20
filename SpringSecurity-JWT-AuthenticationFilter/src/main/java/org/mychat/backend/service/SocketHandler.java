package org.mychat.backend.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.Criteria;

import com.mongodb.BasicDBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.mychat.backend.model.Chat;
import org.mychat.backend.service.mongo.MongoRepository;
import org.mychat.backend.service.repository.HomeRepository;
import org.mychat.backend.users.User;
import org.mychat.backend.users.models.AccountStatus;

@Service
@Configurable
@CrossOrigin(origins = "*")
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoRepository mongoDBService;
	
    @Autowired
    public ChatService chatService;
	List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

        String  msg=String.valueOf(message.getPayload());
        String[] type=msg.split("\\|");
        if(type.length==2){
            User user=mongoDBService.findOne("user", new BasicDBObject("userName",type[1]), User.class);
            if("connect".equals(type[0]))
                user.setStatus(AccountStatus.ACTIVE);
            else
                user.setStatus(AccountStatus.OFFLINE);
            mongoDBService.save("user", user);
        }
        else if(type.length==3){
            Chat chat=new Chat();
            chat.setFrom(type[0]);
            chat.setTo(type[1]);
            chat.setMsg(type[2]);
            chat.setDate(new Date());
            chat.setSeen(false);
            chatService.saveChat(chat);
        }

        for(WebSocketSession websession : sessions) {
           if(websession.isOpen()){
            websession.sendMessage(new TextMessage(msg));
           } 

        }

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//the messages will be broadcasted to all users.
		sessions.add(session);
	}
}
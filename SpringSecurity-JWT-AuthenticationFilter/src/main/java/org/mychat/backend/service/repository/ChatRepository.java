package org.mychat.backend.service.repository;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mychat.backend.model.Chat;
import org.mychat.backend.service.mongo.MongoRepository;

@Service
public class ChatRepository {
    @Autowired
    private MongoRepository mongoDBService;

    public List<Chat> fetchChat(String from,String to,int skip){
    BasicDBObject andQuery = new BasicDBObject();
    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
    obj.add(new BasicDBObject("from", from));
    obj.add(new BasicDBObject("to", to));
    andQuery.put("$and", obj);
        return mongoDBService.findWithSkip("chat", andQuery, Chat.class, skip);
    }

    public void saveChat(Chat chat){
         mongoDBService.save("chat", chat);
    }
}

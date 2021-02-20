package org.mychat.backend.service.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mychat.backend.service.mongo.MongoRepository;
import org.mychat.backend.users.User;

@Service
public class AuthRepository {
    @Autowired
    private MongoRepository mongoDBService;

    public User findByUserName(String collectionName,String userName){
        DBObject findQuery=new BasicDBObject("userName",userName);
        return mongoDBService.findOne(collectionName, findQuery, User.class);
    }
}

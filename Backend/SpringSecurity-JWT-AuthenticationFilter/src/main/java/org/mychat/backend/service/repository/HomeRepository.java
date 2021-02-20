package org.mychat.backend.service.repository;

import java.util.List;

import com.mongodb.BasicDBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.mychat.backend.service.mongo.MongoRepository;
import org.mychat.backend.users.User;
import org.mychat.backend.users.models.AccountStatus;


@Service
public class HomeRepository {
    @Autowired
    MongoRepository mongoDBService;

    public List<User> getUsers(String collectionName,int skip){
        return mongoDBService.findWithSkip(collectionName, new BasicDBObject(), User.class, skip);
    }
    public void updateUserStatus(String userName,String status){
        User user=mongoDBService.findOne("user", new BasicDBObject("userName",userName), User.class);
        if("connect".equals(status))
        user.setStatus(AccountStatus.ACTIVE);
        else
        user.setStatus(AccountStatus.OFFLINE);
        mongoDBService.save("user", user);
    }
}

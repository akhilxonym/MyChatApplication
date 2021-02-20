package org.mychat.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mychat.backend.service.mongo.MongoRepository;
import org.mychat.backend.service.repository.HomeRepository;
import org.mychat.backend.users.User;

@Service
public class HomeService {
    @Autowired
    HomeRepository homeRepository;
    public List<User> getPersons(int skip){
        List<User> users=(List<User>) homeRepository.getUsers("user",skip);
        return users;
    }
}

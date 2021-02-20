package org.mychat.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mychat.backend.model.Chat;
import org.mychat.backend.service.repository.ChatRepository;

@Service
public class ChatService {
    @Autowired
    public ChatRepository chatRepository;
 
    public List<Chat> fetchChat(String from,String to,int skip){
        return chatRepository.fetchChat(from,to,skip);
    }

    public void saveChat(Chat chat){
         chatRepository.saveChat(chat);
    }
}

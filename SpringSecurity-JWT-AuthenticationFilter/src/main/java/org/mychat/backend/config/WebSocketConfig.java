package org.mychat.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.mychat.backend.service.SocketHandler;

@Configuration
@CrossOrigin(origins = "*")
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    public SocketHandler socketHandler;
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/name");
        
    }

    
    
}
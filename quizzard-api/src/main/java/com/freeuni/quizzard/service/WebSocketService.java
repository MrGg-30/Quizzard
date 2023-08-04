package com.freeuni.quizzard.service;

import com.freeuni.quizzard.model.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendToUser(String userId, FriendRequest message) {
        messagingTemplate.convertAndSendToUser(userId,"/private",message);
    }
}

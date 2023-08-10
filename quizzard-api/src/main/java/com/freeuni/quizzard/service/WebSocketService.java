package com.freeuni.quizzard.service;

import com.freeuni.quizzard.dto.QuestionDto;
import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.model.GameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendFriendRequest(String userId, FriendRequest message) {
        messagingTemplate.convertAndSendToUser(userId,"/friend-request", message);
    }

    public void sendFriendResponse(String userId, FriendRequest message) {
        messagingTemplate.convertAndSendToUser(userId,"/friend-response", message);
    }

    public void sendGameRequest(String userId, GameRequest gameRequest) {
        messagingTemplate.convertAndSendToUser(userId,"/game-request", gameRequest);
    }

    public void sendGameResponse(String userId, GameRequest message) {
        messagingTemplate.convertAndSendToUser(userId,"/game-response", message);
    }

    public void sendSessionId(String userId, GameRequest message) {
        messagingTemplate.convertAndSendToUser(userId, "/session-id", message);
    }

    public void sendQuestion(String sessionId, QuestionDto questionDto) {
        messagingTemplate.convertAndSendToUser(sessionId, "/question", questionDto);
    }
}

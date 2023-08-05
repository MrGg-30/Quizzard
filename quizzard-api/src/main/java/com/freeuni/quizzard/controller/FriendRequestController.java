package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.service.FriendRequestService;
import com.freeuni.quizzard.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class FriendRequestController {

    private final WebSocketService webSocketService;

    private final FriendRequestService friendRequestService;

    @MessageMapping("/friend-request")
    public FriendRequest friendRequest(@Payload FriendRequest message){
        System.out.println(message);
        friendRequestService.createNewRequest(message);
        webSocketService.sendFriendRequest(message.getTo(), message);
        return message;
    }

    @MessageMapping("/friend-response")
    public void friendResponse(@Payload FriendRequest message) {
        message.setStatus(message.getStatus());
        System.out.println(message);
        webSocketService.sendFriendResponse(message.getFrom(), message);
        friendRequestService.friendResponse(message);
    }


    @GetMapping("/pendingRequests")
    public ResponseEntity<List<FriendRequest>> getReceivedPendingRequests(@RequestParam String username) {
        List<FriendRequest> requests = friendRequestService.getReceivedRequests(username);
        return ResponseEntity.ok(requests);
    }
}

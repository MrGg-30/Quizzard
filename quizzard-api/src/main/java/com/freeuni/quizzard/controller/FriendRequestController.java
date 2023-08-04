package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.model.RequestStatus;
import com.freeuni.quizzard.service.FriendRequestService;
import com.freeuni.quizzard.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @MessageMapping("/application")
    @SendTo("/all/messages")
    public FriendRequest send(FriendRequest message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        System.out.println(message);
        return message;
    }

    @MessageMapping("/private")
    public FriendRequest friendRequest(@Payload FriendRequest message){
        System.out.println(message);
        friendRequestService.createNewRequest(message);
        webSocketService.sendToUser(message.getTo(), message);
        return message;
    }

    @PostMapping("/accept")
    public void acceptRequest(@RequestBody FriendRequest request) {
        request.setStatus(RequestStatus.ACCEPTED);
        System.out.println(request);
        friendRequestService.acceptRequest(request);
    }

    @PostMapping("/deny")
    public void denyRequest(@RequestBody FriendRequest request) {
        request.setStatus(RequestStatus.DECLINED);
        System.out.println(request);
        friendRequestService.declineRequest(request);
    }

    @GetMapping("/pendingRequests")
    public ResponseEntity<List<FriendRequest>> getReceivedPendingRequests(@RequestParam String username) {
        List<FriendRequest> requests = friendRequestService.getReceivedRequests(username);
        return ResponseEntity.ok(requests);
    }
}

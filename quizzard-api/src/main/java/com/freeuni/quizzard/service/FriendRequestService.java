package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import com.freeuni.quizzard.data.mongo.repository.FriendsRepository;
import com.freeuni.quizzard.mapper.FriendRequestMapper;
import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.model.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendsRepository friendsRepository;

    private final FriendRequestMapper mapper;

    public void createNewRequest(FriendRequest request) {
        friendsRepository.insert(mapper.toFriendRequestEntity(request));
    }

    public void acceptRequest(FriendRequest request) {
        FriendRequestEntity requestToUpdate = friendsRepository.findByRequestSenderAndRequestReceiver(request.getFrom(), request.getTo());
        if (requestToUpdate != null) {
            requestToUpdate.setStatus(RequestStatus.ACCEPTED);
            friendsRepository.save(requestToUpdate);
        }
    }

    public void declineRequest(FriendRequest request) {
        FriendRequestEntity requestToUpdate = friendsRepository.findByRequestSenderAndRequestReceiver(request.getFrom(), request.getTo());
        if (requestToUpdate != null) {
            requestToUpdate.setStatus(RequestStatus.DECLINED);
            friendsRepository.save(requestToUpdate);
        }
    }
}

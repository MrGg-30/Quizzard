package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import com.freeuni.quizzard.data.mongo.repository.FriendsRepository;
import com.freeuni.quizzard.mapper.FriendRequestMapper;
import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.model.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendsRepository friendsRepository;

    private final FriendRequestMapper mapper;

    private final UserService userService;

    public void createNewRequest(FriendRequest request) {
        friendsRepository.save(mapper.toFriendRequestEntity(request));
    }

    public void friendResponse(FriendRequest request) {
        FriendRequestEntity requestToUpdate = friendsRepository.findByRequestSenderAndRequestReceiver(request.getTo(), request.getFrom());
        if (requestToUpdate != null) {
            requestToUpdate.setStatus(request.getStatus());
            if (requestToUpdate.getStatus() == RequestStatus.ACCEPTED) {
                userService.addNewFriend(request.getFrom(), request.getTo());
                userService.addNewFriend(request.getTo(), request.getFrom());
                friendsRepository.delete(requestToUpdate);
            } else if (requestToUpdate.getStatus() == RequestStatus.DECLINED) {
                friendsRepository.delete(requestToUpdate);
            }
        }
    }

    public List<FriendRequest> getReceivedRequests(String username) {
        List<FriendRequestEntity> requestEntities = friendsRepository.findAllByRequestReceiverAndStatus(username, RequestStatus.PENDING);
        return requestEntities
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }
}

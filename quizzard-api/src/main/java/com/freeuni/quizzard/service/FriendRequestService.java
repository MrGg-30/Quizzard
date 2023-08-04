package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import com.freeuni.quizzard.data.mongo.repository.FriendsRepository;
import com.freeuni.quizzard.mapper.FriendRequestMapper;
import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.model.RequestStatus;
import lombok.RequiredArgsConstructor;
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
        friendsRepository.insert(mapper.toFriendRequestEntity(request));
    }

    public void acceptRequest(FriendRequest request) {
        FriendRequestEntity requestToUpdate = friendsRepository.findByRequestSenderAndRequestReceiver(request.getFrom(), request.getTo());
        if (requestToUpdate != null) {
            requestToUpdate.setStatus(RequestStatus.ACCEPTED);
            friendsRepository.save(requestToUpdate);

            userService.addNewFriend(request.getFrom(), request.getTo());
            userService.addNewFriend(request.getTo(), request.getFrom());
        }
    }

    public void declineRequest(FriendRequest request) {
        FriendRequestEntity requestToUpdate = friendsRepository.findByRequestSenderAndRequestReceiver(request.getFrom(), request.getTo());
        if (requestToUpdate != null) {
            requestToUpdate.setStatus(RequestStatus.DECLINED);
            friendsRepository.save(requestToUpdate);
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

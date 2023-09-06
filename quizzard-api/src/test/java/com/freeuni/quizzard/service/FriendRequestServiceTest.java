package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import com.freeuni.quizzard.data.mongo.repository.FriendsRepository;
import com.freeuni.quizzard.mapper.FriendRequestMapper;
import com.freeuni.quizzard.model.FriendRequest;
import com.freeuni.quizzard.model.RequestStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FriendRequestServiceTest {

    @Mock
    private FriendsRepository friendsRepository;

    @Mock
    private FriendRequestMapper mapper;

    @Mock
    private UserService userService;

    private FriendRequestService friendRequestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        friendRequestService = new FriendRequestService(friendsRepository, mapper, userService);
    }

    @Test
    public void testCreateNewRequest() {
        FriendRequest friendRequest = new FriendRequest();

        when(mapper.toFriendRequestEntity(friendRequest)).thenReturn(new FriendRequestEntity());

        friendRequestService.createNewRequest(friendRequest);

        verify(friendsRepository).save(any(FriendRequestEntity.class));
    }

    @Test
    public void testFriendResponseAccepted() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFrom("UserA");
        friendRequest.setTo("UserB");
        friendRequest.setStatus(RequestStatus.ACCEPTED);

        FriendRequestEntity requestEntity = new FriendRequestEntity();
        when(friendsRepository.findByRequestSenderAndRequestReceiver("UserB", "UserA")).thenReturn(requestEntity);

        friendRequestService.friendResponse(friendRequest);

        verify(userService).addNewFriend("UserA", "UserB");
        verify(userService).addNewFriend("UserB", "UserA");
        verify(friendsRepository).delete(requestEntity);
    }

    @Test
    public void testFriendResponseDeclined() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFrom("UserA");
        friendRequest.setTo("UserB");
        friendRequest.setStatus(RequestStatus.DECLINED);

        FriendRequestEntity requestEntity = new FriendRequestEntity();
        when(friendsRepository.findByRequestSenderAndRequestReceiver("UserB", "UserA")).thenReturn(requestEntity);

        friendRequestService.friendResponse(friendRequest);

        verify(friendsRepository).delete(requestEntity);
    }

    @Test
    public void testGetReceivedRequests() {
        List<FriendRequestEntity> requestEntities = new ArrayList<>();
        when(friendsRepository.findAllByRequestReceiverAndStatus(anyString(), any(RequestStatus.class))).thenReturn(requestEntities);

        when(mapper.fromEntity(any(FriendRequestEntity.class))).thenReturn(new FriendRequest());

        List<FriendRequest> receivedRequests = friendRequestService.getReceivedRequests("UserA");

        verify(mapper, Mockito.times(requestEntities.size())).fromEntity(any(FriendRequestEntity.class));

        assertEquals(requestEntities.size(), receivedRequests.size());
    }
}

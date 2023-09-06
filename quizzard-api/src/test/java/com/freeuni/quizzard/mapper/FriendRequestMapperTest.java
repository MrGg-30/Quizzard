package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import com.freeuni.quizzard.model.FriendRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FriendRequestMapperTest {

    private FriendRequestMapper friendRequestMapper;

    @BeforeEach
    public void setUp() {
        friendRequestMapper = Mappers.getMapper(FriendRequestMapper.class);
    }

    @Test
    public void testToFriendRequestEntity() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFrom("UserA");
        friendRequest.setTo("UserB");

        FriendRequestEntity entity = friendRequestMapper.toFriendRequestEntity(friendRequest);

        assertEquals("UserA", entity.getRequestSender());
        assertEquals("UserB", entity.getRequestReceiver());
    }

    @Test
    public void testFromEntity() {
        FriendRequestEntity entity = new FriendRequestEntity();
        entity.setRequestSender("UserX");
        entity.setRequestReceiver("UserY");

        FriendRequest friendRequest = friendRequestMapper.fromEntity(entity);

        assertEquals("UserX", friendRequest.getFrom());
        assertEquals("UserY", friendRequest.getTo());
    }
}

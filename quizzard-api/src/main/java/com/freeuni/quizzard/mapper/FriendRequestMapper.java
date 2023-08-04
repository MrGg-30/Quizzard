package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import com.freeuni.quizzard.model.FriendRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FriendRequestMapper {

    @Mapping(source = "from", target = "requestSender")
    @Mapping(source = "to", target = "requestReceiver")
    FriendRequestEntity toFriendRequestEntity(FriendRequest friendRequest);

    @Mapping(source = "requestSender", target = "from")
    @Mapping(source = "requestReceiver", target = "to")
    FriendRequest fromEntity(FriendRequestEntity friendRequestEntity);
}

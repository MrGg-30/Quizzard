package com.freeuni.quizzard.data.mongo.repository;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepository extends MongoRepository<FriendRequestEntity, String> {

    FriendRequestEntity findByRequestSenderAndRequestReceiver(String requestSender, String requestReceiver);
}

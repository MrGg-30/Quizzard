package com.freeuni.quizzard.data.mongo.repository;

import com.freeuni.quizzard.data.mongo.model.FriendRequestEntity;
import com.freeuni.quizzard.model.RequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends MongoRepository<FriendRequestEntity, String> {

    FriendRequestEntity findByRequestSenderAndRequestReceiver(String requestSender, String requestReceiver);

    List<FriendRequestEntity> findAllByRequestReceiverAndStatus(String requestReceiver, RequestStatus status);
}

package com.freeuni.quizzard.data.mongo.model;

import com.freeuni.quizzard.model.RequestStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "friends")
public class FriendRequestEntity {

    @Id
    private String id;

    private String requestSender;

    private String requestReceiver;

    private RequestStatus status;
}

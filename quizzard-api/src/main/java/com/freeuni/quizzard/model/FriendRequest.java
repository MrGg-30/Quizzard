package com.freeuni.quizzard.model;

import lombok.Data;

@Data
public class FriendRequest {

    private String from;
    private String to;
    private String text;
    private RequestStatus status;
}

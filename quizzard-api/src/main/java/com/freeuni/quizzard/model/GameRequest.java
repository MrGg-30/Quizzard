package com.freeuni.quizzard.model;

import lombok.Data;

@Data
public class GameRequest {

    private String category;
    private String to;
    private String from;
    private RequestStatus status;
}
package com.freeuni.quizzard.model;

import lombok.Data;

@Data
public class GameStartRequest {

    private String username1;

    private String username2;

    private String category;
}

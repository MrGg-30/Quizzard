package com.freeuni.quizzard.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;

    private String name;

    private String lastName;

    private String username;

    private String email;

    private String profilePictureUrl;
}

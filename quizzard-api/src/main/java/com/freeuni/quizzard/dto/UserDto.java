package com.freeuni.quizzard.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String name;

    private String lastName;

    private String username;

    private String email;

    private List<String> friends;

    private String profilePictureUrl;

    private Integer quizCount;

    private Integer wonQuizCount;

    private Integer totalPoints;
}

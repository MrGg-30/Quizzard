package com.freeuni.quizzard.data.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;

    private String lastName;

    private String username;

    private String email;

    private String profilePictureUrl;
}


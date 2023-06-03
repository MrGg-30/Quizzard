package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.config.KeycloakConfig;
import com.freeuni.quizzard.model.UserCreationAttributes;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final KeycloakConfig config;


    public UserController(KeycloakConfig config) {
        this.config = config;
    }

    @PostMapping("/create")
    public String addUser(@RequestBody UserCreationAttributes user) {
        config.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
        return "created";
    }
}

package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.AlreadyUsedException;
import com.freeuni.quizzard.config.KeycloakConfig;
import com.freeuni.quizzard.model.UserCreationAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final KeycloakConfig config;


    public UserController(KeycloakConfig config) {
        this.config = config;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> addUser(@RequestBody UserCreationAttributes user) {
        config.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @ExceptionHandler(AlreadyUsedException.class)
    public ResponseEntity<Object> handleDuplicateResourceException(AlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}

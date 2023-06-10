package com.freeuni.quizzard.controller;

import com.freeuni.quizzard.exception.UserAlreadyExistsException;
import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.freeuni.quizzard.service.UserService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> addUser(@RequestBody UserCreationAttributes user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).
                body("User was created successfully");
    }

    // secured endpoint
    @GetMapping("/email")
    public String getEmail(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String email = (String) token.getTokenAttributes().get("email");
        return email;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleDuplicateResourceException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}

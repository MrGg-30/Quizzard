package com.freeuni.quizzard.service;

import com.freeuni.quizzard.config.KeycloakConfig;
import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final KeycloakConfig config;

    public void createUser(UserCreationAttributes user) {
        config.createNewUser(user.getUsername(), user.getEmail(), user.getPassword());
    }
}

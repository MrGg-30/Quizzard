package com.freeuni.quizzard.service;

import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakService keycloakService;

    public void createUser(UserCreationAttributes user) {
        keycloakService.createNewUser(user.getUsername(), user.getEmail(), user.getPassword());
    }
}

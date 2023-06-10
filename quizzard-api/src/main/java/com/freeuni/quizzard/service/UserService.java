package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.User;
import com.freeuni.quizzard.data.mongo.repository.UserRepository;
import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakService keycloakService;
    private final UserRepository userRepository;


    public void createUser(UserCreationAttributes user) {
        keycloakService.createNewUser(user.getUsername(), user.getEmail(), user.getPassword());
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());
        userRepository.insert(user1);
    }
}

package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.User;
import com.freeuni.quizzard.data.mongo.repository.UserRepository;
import com.freeuni.quizzard.dto.UserDto;
import com.freeuni.quizzard.mapper.UserMapper;
import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakService keycloakService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(UserCreationAttributes user) {
        keycloakService.createNewUser(user.getUsername(), user.getEmail(), user.getPassword());
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());
        userRepository.insert(user1);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<UserDto> getUserByUsernamePrefix(String usernamePrefix) {
        List<User> users = userRepository.findUsersByUsernamePrefix(usernamePrefix);
        return userMapper.toDtoList(users);
    }
}

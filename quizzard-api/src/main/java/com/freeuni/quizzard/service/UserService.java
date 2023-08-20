package com.freeuni.quizzard.service;

import com.freeuni.quizzard.data.mongo.model.User;
import com.freeuni.quizzard.data.mongo.repository.UserRepository;
import com.freeuni.quizzard.dto.UserDto;
import com.freeuni.quizzard.mapper.UserMapper;
import com.freeuni.quizzard.model.UserCreationAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakService keycloakService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final S3Client amazonS3;
    private final String bucketName = "quizzard-pictures";

    public void createUser(UserCreationAttributes userAttributes) {
        keycloakService.createNewUser(userAttributes.getUsername(), userAttributes.getEmail(), userAttributes.getPassword());
        User newUser = new User();
        newUser.setUsername(userAttributes.getUsername());
        newUser.setEmail(userAttributes.getEmail());
        newUser.setName(userAttributes.getName());
        newUser.setLastName(userAttributes.getLastName());
        newUser.setFriends(Collections.emptyList());
        newUser.setQuizCount(0);
        newUser.setWonQuizCount(0);
        newUser.setTotalPoints(0);
        newUser.setProfilePictureUrl(defaultPictureUrl());
        userRepository.save(newUser);
    }

    public UserDto getUser(String username) {
        return userMapper.toDto(userRepository.findUserByUsername(username));
    }

    public void addNewFriend(String username, String friendUsername) {
        User user = userRepository.findUserByUsername(username);
        user.getFriends().add(friendUsername);
        userRepository.save(user);
    }

    public List<UserDto> getUserByUsernamePrefix(String usernamePrefix) {
        List<User> users = userRepository.findUsersByUsernamePrefix(usernamePrefix);
        return userMapper.toDtoList(users);
    }

    public void addScore(String username, int score) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            // NOT FOUND USER
            return;
        }
        user.setTotalPoints(user.getTotalPoints() + score);
        user.setQuizCount(user.getQuizCount() + 1);
        userRepository.save(user);
    }

    public void addWinningCount(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            // NOT FOUND USER
            return;
        }
        user.setWonQuizCount(user.getWonQuizCount() + 1);
        userRepository.save(user);
    }

    @Transactional
    public String upload(MultipartFile file, String username) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(file.getBytes());
            String key = username + "-profile-picture";

            User user = userRepository.findUserByUsername(username);
            if (user == null) {
                return "user not found";
            }
            amazonS3.putObject(builder -> builder.bucket(bucketName).key(key), RequestBody.fromByteBuffer(byteBuffer));

            user.setProfilePictureUrl(key);
            userRepository.save(user);

            return "Successfully";
        } catch (IOException e) {
            return "Fail";
        }
    }

    public byte[] getProfilePictureByName(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            // User not found
            return null;
        }
        String url = user.getProfilePictureUrl();
        if (url == null) {
            // TODO default photo
            return null;
        }

        GetObjectRequest objectRequest = GetObjectRequest
                .builder()
                .key(url)
                .bucket(bucketName)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = amazonS3.getObjectAsBytes(objectRequest);
        return objectBytes.asByteArray();
    }

    private String defaultPictureUrl() {
        Random rn = new Random();
        int color = rn.nextInt(4) + 1;
        return "default" + color + ".png";
    }
}

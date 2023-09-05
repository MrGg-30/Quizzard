package com.freeuni.quizzard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import com.freeuni.quizzard.data.mongo.model.User;
import com.freeuni.quizzard.data.mongo.repository.UserRepository;
import com.freeuni.quizzard.dto.UserDto;
import com.freeuni.quizzard.mapper.UserMapper;
import com.freeuni.quizzard.model.UserCreationAttributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testCreateUser() {
//        UserCreationAttributes attributes = new UserCreationAttributes();
//        attributes.setUsername("user1");
//        attributes.setEmail("user1@example.com");
//        attributes.setName("Test");
//        attributes.setLastName("User");
//
//        userService.createUser(attributes);
//
//        verify(userRepository, times(1)).save(any(User.class));
//    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setUsername("user1");
        when(userRepository.findUserByUsername("user1")).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(new UserDto().setUsername("user1"));

        UserDto result = userService.getUser("user1");

        assertEquals(result.getUsername(),"user1");
        verify(userRepository, times(1)).findUserByUsername("user1");
    }

    @Test
    public void testAddNewFriend() {
        User user = new User();
        user.setUsername("user1");
        user.setFriends(new ArrayList<>());
        when(userRepository.findUserByUsername("user1")).thenReturn(user);

        userService.addNewFriend("user1", "user2");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserByUsernamePrefix() {
        List<User> users = new ArrayList<>();
        users.add(new User().setUsername("user1"));
        users.add(new User().setUsername("user2"));
        users.add(new User().setUsername("user3"));
        users.add(new User().setUsername("testUser1"));
        when(userRepository.findUsersByUsernamePrefix("user")).thenReturn(users);
        when(userMapper.toDtoList(users)).thenReturn(List.of(new UserDto(),new UserDto(),new UserDto()));

        List<UserDto> usersResult = userService.getUserByUsernamePrefix("user");

        assertEquals(3, usersResult.size());
        verify(userRepository, times(1)).findUsersByUsernamePrefix("user");
    }

    @Test
    public void testAddScore() {
        User user = new User();
        user.setTotalPoints(10);
        user.setQuizCount(2);
        when(userRepository.findUserByUsername("testuser")).thenReturn(user);

        userService.addScore("testuser", 5);

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(15, user.getTotalPoints());
        assertEquals(3, user.getQuizCount());
    }

    @Test
    public void testAddWinningCount() {
        User user = new User();
        user.setWonQuizCount(2);
        when(userRepository.findUserByUsername("testuser")).thenReturn(user);

        userService.addWinningCount("testuser");

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(3, user.getWonQuizCount());
    }

//    @Test
//    public void testUpload() throws IOException {
//        MultipartFile multipartFile = mock(MultipartFile.class);
//        when(multipartFile.getBytes()).thenReturn(new byte[0]);
//
//        User user = new User();
//        when(userRepository.findUserByUsername("testuser")).thenReturn(user);
//
//        String result = userService.upload(multipartFile, "testuser");
//
//        verify(userRepository, times(1)).save(any(User.class));
//        assertEquals("Successfully", result);
//    }

    @Test
    public void testGetProfilePictureByName_UserNotFound() {
        when(userRepository.findUserByUsername("testuser")).thenReturn(null);

        byte[] result = userService.getProfilePictureByName("testuser");

        assertNull(result);
    }
}

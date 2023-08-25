package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.User;
import com.freeuni.quizzard.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testUserToUserDtoMapping() {
        User user = new User();
        user.setId("123");
        user.setName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        user.setProfilePictureUrl("profile.jpg");
        user.setFriends(Arrays.asList("friend1", "friend2"));
        user.setQuizCount(5);
        user.setWonQuizCount(2);
        user.setTotalPoints(100);

        UserDto userDto = userMapper.toDto(user);

        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getProfilePictureUrl(), userDto.getProfilePictureUrl());
        assertEquals(user.getFriends(), userDto.getFriends());
        assertEquals(user.getQuizCount(), userDto.getQuizCount());
        assertEquals(user.getWonQuizCount(), userDto.getWonQuizCount());
        assertEquals(user.getTotalPoints(), userDto.getTotalPoints());
    }

    @Test
    public void testUserListToUserDtoListMapping() {
        User user1 = new User();
        user1.setName("Alice");
        User user2 = new User();
        user2.setName("Bob");
        List<User> userList = Arrays.asList(user1, user2);

        List<UserDto> userDtoList = userMapper.toDtoList(userList);

        assertEquals(userList.size(), userDtoList.size());
        assertEquals(userList.get(0).getName(), userDtoList.get(0).getName());
        assertEquals(userList.get(1).getName(), userDtoList.get(1).getName());
    }
}

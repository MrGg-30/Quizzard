package com.freeuni.quizzard.mapper;

import com.freeuni.quizzard.data.mongo.model.User;
import com.freeuni.quizzard.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> users);
}

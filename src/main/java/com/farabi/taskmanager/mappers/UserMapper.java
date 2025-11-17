package com.farabi.taskmanager.mappers;

import com.farabi.taskmanager.dtos.UserDto;
import com.farabi.taskmanager.entities.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUserEntity(UserDto userDto);
}

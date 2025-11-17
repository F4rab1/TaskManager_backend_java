package com.farabi.taskmanager.mappers;

import com.farabi.taskmanager.dtos.RegisterUserRequest;
import com.farabi.taskmanager.dtos.UpdateUserRequest;
import com.farabi.taskmanager.dtos.UserDto;
import com.farabi.taskmanager.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUserEntity(RegisterUserRequest registerUserRequest);

    void updateUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}


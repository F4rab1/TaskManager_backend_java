package com.farabi.taskmanager.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUserEntity(RegisterUserRequest registerUserRequest);

    void updateUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}


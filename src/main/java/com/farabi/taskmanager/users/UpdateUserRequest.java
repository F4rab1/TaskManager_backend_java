package com.farabi.taskmanager.users;

import lombok.Data;


@Data
public class UpdateUserRequest {
    public String name;
    public String email;
}

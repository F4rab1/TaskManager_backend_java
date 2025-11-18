package com.farabi.taskmanager.dtos;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;

    private String password;
}

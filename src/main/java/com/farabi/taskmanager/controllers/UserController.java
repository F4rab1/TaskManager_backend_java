package com.farabi.taskmanager.controllers;

import com.farabi.taskmanager.dtos.ErrorDto;
import com.farabi.taskmanager.dtos.RegisterUserRequest;
import com.farabi.taskmanager.dtos.UpdateUserRequest;
import com.farabi.taskmanager.dtos.UserDto;
import com.farabi.taskmanager.entities.Role;
import com.farabi.taskmanager.mappers.UserMapper;
import com.farabi.taskmanager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody RegisterUserRequest registerUserRequest,
            UriComponentsBuilder uriBuilder
    ) {
        if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
            return ResponseEntity.badRequest().body(
                    new ErrorDto("Email already exists!")
            );
        }

        var user = userMapper.toUserEntity(registerUserRequest);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRole(Role.USER);
        user = userRepository.save(user);

        var userDto = userMapper.toUserDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.updateUser(updateUserRequest, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toUserDto(user));
    }
}

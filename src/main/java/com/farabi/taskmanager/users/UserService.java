package com.farabi.taskmanager.users;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return userMapper.toUserDto(user);
    }

    public UserDto createUser(RegisterUserRequest registerUserRequest) {
        if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
            throw new DuplicateUserException();
        }

        var user = userMapper.toUserEntity(registerUserRequest);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRole(Role.USER);
        user = userRepository.save(user);

        return userMapper.toUserDto(user);
    }


    public UserDto updateUser(Long id, UpdateUserRequest updateUserRequest) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userMapper.updateUser(updateUserRequest, user);
        userRepository.save(user);

        return userMapper.toUserDto(user);
    }
}

package com.blockbank.blockbank.service.impl;

import com.blockbank.blockbank.dto.request.UserRequestDto;
import com.blockbank.blockbank.dto.response.UserResponseDto;
import com.blockbank.blockbank.entity.User;
import com.blockbank.blockbank.repository.UserRepository;
import com.blockbank.blockbank.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDto register(UserRequestDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        User saved = userRepository.save(user);

        return UserResponseDto.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .build();
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}

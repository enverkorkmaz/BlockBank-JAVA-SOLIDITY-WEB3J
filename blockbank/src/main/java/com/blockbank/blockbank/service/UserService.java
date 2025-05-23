package com.blockbank.blockbank.service;

import com.blockbank.blockbank.dto.request.UserRequestDto;
import com.blockbank.blockbank.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRequestDto dto);
    UserResponseDto getUserByUsername(String username);
}

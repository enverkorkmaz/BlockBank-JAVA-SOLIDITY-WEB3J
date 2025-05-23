package com.blockbank.blockbank.service;

import com.blockbank.blockbank.dto.request.LoginRequest;
import com.blockbank.blockbank.dto.request.RegisterRequest;
import com.blockbank.blockbank.dto.response.AuthResponse;
import com.blockbank.blockbank.dto.response.UserResponseDto;

public interface AuthService {
    UserResponseDto register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}

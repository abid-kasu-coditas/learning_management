package com.example.learning_management.service;


import com.example.learning_management.dto.request.LoginRequest;
import com.example.learning_management.dto.request.RefreshTokenRequest;
import com.example.learning_management.dto.request.RegisterRequest;
import com.example.learning_management.dto.response.AuthResponse;
import com.example.learning_management.dto.response.RegisterResponse;
import com.example.learning_management.entitiy.User;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(Long userId);

    String getLoggedInEmployee();

}

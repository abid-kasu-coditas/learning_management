package com.example.learning_management.controller;

import com.example.learning_management.dto.request.LoginRequest;
import com.example.learning_management.dto.request.RefreshTokenRequest;
import com.example.learning_management.dto.request.RegisterRequest;
import com.example.learning_management.dto.response.ApiResponse;
import com.example.learning_management.dto.response.AuthResponse;
import com.example.learning_management.dto.response.RegisterResponse;
import com.example.learning_management.security.CustomUserDetails;
import com.example.learning_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {

        RegisterResponse authResponse = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Registration Successfull", authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Login Successfull", authResponse));

    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody RefreshTokenRequest request) {

        AuthResponse authResponse = authService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Token generated", authResponse));

    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        authService.logout(customUserDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));

    }

}

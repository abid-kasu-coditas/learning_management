package com.example.learning_management.controller;

import com.example.learning_management.dto.request.LoginRequest;
import com.example.learning_management.dto.request.RefreshTokenRequest;
import com.example.learning_management.dto.request.RegisterRequest;
import com.example.learning_management.dto.response.ApplicationResponse;
import com.example.learning_management.dto.response.AuthResponse;
import com.example.learning_management.dto.response.GeneralResponse;
import com.example.learning_management.dto.response.RegisterResponse;
import com.example.learning_management.exception.AuthenticationException;
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
    public ResponseEntity<ApplicationResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApplicationResponse<>(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApplicationResponse<>(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApplicationResponse<AuthResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {

        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApplicationResponse<>(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null || customUserDetails.getUser() == null) {
            throw new AuthenticationException("Unauthorized");
        }
        authService.logout(customUserDetails.getUser().getId());
        return ResponseEntity.ok(new ApplicationResponse<>(new GeneralResponse("Logged out successfully")));
    }

}

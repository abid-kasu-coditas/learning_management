package com.example.learning_management.service.impl;

import com.example.learning_management.dto.request.LoginRequest;
import com.example.learning_management.dto.request.RefreshTokenRequest;
import com.example.learning_management.dto.request.RegisterRequest;
import com.example.learning_management.dto.response.AuthResponse;
import com.example.learning_management.dto.response.RegisterResponse;
import com.example.learning_management.entitiy.EmployeeDetails;
import com.example.learning_management.entitiy.RefreshToken;
import com.example.learning_management.entitiy.User;
import com.example.learning_management.enums.Role;
import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.exception.AlreadyExistException;
import com.example.learning_management.exception.AuthenticationException;
import com.example.learning_management.repository.UserRepository;
import com.example.learning_management.security.CustomUserDetails;
import com.example.learning_management.security.JwtService;
import com.example.learning_management.service.AuthService;
import com.example.learning_management.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Value("${app.jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistException(ExceptionConstants.USER_ALREADY_EXISTS_EMAIL + request.getEmail());
        }
        Role role = Role.valueOf(request.getRole());
        User userToSave = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        if (role == Role.EMPLOYEE) {
            if (request.getDepartment() == null || request.getStatus() == null) {
                throw new IllegalArgumentException(ExceptionConstants.NOT_CORRECT_BODY);
            }
            EmployeeDetails employeeDetails = EmployeeDetails.builder()
                    .department(request.getDepartment())
                    .status(request.getStatus())
                    .user(userToSave)
                    .build();
            userToSave.setEmployeeDetails(employeeDetails);
        }

        User savedUser = userRepository.save(userToSave);
        return RegisterResponse.builder()
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user =userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AuthenticationException(ExceptionConstants.USER_NOT_FOUND_EMAIL + request.getEmail()));
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(customUserDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return toAuthResponse(accessToken, refreshToken.getToken(), user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken()).orElseThrow(() -> new AuthenticationException(ExceptionConstants.REFRESH_TOKEN_NOT_FOUND));
        refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String newAccessToken = jwtService.generateAccessToken(customUserDetails);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
        return toAuthResponse(newAccessToken, newRefreshToken.getToken(), user);
    }

    @Override
    public void logout(Long userId) {

        refreshTokenService.deleteByUserId(userId);
    }

    private AuthResponse toAuthResponse(String accessToken, String refreshToken, User user) {

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(accessTokenExpiration)
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public String getLoggedInEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new AuthenticationException("User is not found");
    }

}

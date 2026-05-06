package com.example.learning_management.service.impl;

import com.example.learning_management.dto.request.LoginRequest;
import com.example.learning_management.dto.request.RefreshTokenRequest;
import com.example.learning_management.dto.request.RegisterRequest;
import com.example.learning_management.dto.response.AuthResponse;
import com.example.learning_management.dto.response.RegisterResponse;
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

import java.util.Objects;

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
        User userToSave = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.EMPLOYEE)
                .build();

        User savedUser = userRepository.save(userToSave);
        return RegisterResponse.builder()
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticate(request.getEmail(), request.getPassword());
        User user = findUserByEmail(request.getEmail());
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(customUserDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return buildAuthResponse(accessToken, refreshToken.getToken(), user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new AuthenticationException(ExceptionConstants.REFRESH_TOKEN_NOT_FOUND));
        refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String newAccessToken = jwtService.generateAccessToken(customUserDetails);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
        return buildAuthResponse(newAccessToken, newRefreshToken.getToken(), user);
    }

    @Override
    public void logout(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }

    private AuthResponse buildAuthResponse(String accessToken, String refreshToken, User user) {

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
        if (authentication != null
                && authentication.isAuthenticated()
                && !Objects.equals(authentication.getName(), "anonymousUser")) {
            return authentication.getName();
        }
        throw new AuthenticationException("No authenticated user found in security context");
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(ExceptionConstants.USER_NOT_FOUND_EMAIL + email));
    }
}

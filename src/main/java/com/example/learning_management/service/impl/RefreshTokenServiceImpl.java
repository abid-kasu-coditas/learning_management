package com.example.learning_management.service.impl;

import com.example.learning_management.entitiy.RefreshToken;
import com.example.learning_management.entitiy.User;
import com.example.learning_management.exception.ErrorConstants;
import com.example.learning_management.repository.RefreshTokenRepository;
import com.example.learning_management.repository.UserRepository;
import com.example.learning_management.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${app.jwt.refreh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public RefreshToken createRefreshToken(Long userId) {

        refreshTokenRepository.findByUserId(userId).ifPresent(refreshTokenRepository::delete);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(ErrorConstants.USER_NOT_FOUND + "id"));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(Instant.now().plusMillis(refreshTokenExpiration))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
//    THROW EXCEPTION HERE
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {

        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteByUserId(Long userId) {

        refreshTokenRepository.deleteByUserId(userId);

    }
}

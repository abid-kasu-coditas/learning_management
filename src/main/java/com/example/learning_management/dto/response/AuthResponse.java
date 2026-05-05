package com.example.learning_management.dto.response;

import com.example.learning_management.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private Long userId;

    private String email;

    @Builder.Default
    private String tokenType = "Bearer";

    private String accessToken;

    private String refreshToken;

    private long expiresIn;

    private String role;


}

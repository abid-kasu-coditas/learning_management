package com.example.learning_management.constants;

public final class SecurityConstants {

    private SecurityConstants() {

    }

    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };
}

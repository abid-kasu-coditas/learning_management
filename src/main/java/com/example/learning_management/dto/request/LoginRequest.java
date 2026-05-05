package com.example.learning_management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NotBlank
@Builder
public class LoginRequest {

    @Email(message = "must be valid email address")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}

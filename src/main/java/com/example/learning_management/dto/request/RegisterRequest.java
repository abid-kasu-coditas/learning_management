package com.example.learning_management.dto.request;

import com.example.learning_management.enums.Department;
import com.example.learning_management.enums.EmployeeStatus;
import com.example.learning_management.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password is required")
    private String password;

    private Department department;

    private EmployeeStatus status;

    @NotBlank(message = "Role is required")
    private String role;

}

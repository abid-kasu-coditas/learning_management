package com.example.learning_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {

    @NotBlank(message = "course title is required")
    private String title;

    @NotBlank(message = "course description is required")
    private String description;

}

package com.example.learning_management.dto.response;

import com.example.learning_management.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Long id;

    private String title;

    private String description;

//    private CourseStatus courseStatus;

    private int totalLectures;

}

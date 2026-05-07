package com.example.learning_management.dto.response;


import com.example.learning_management.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {

    private Long id;

    private Long courseId;

    private String courseTitle;

    private EnrollmentStatus status;

    private LocalDateTime enrolledAt;

}

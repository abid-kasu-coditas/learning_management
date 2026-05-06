package com.example.learning_management.dto.response;

import com.example.learning_management.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseProgressResponse {

    private Long id;

    private String courseTitle;

    private Long totalLectures;

    private Long completedLectures;

    private Long completePercentage;

    private EnrollmentStatus enrollmentStatus;


}

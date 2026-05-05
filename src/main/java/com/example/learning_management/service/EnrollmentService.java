package com.example.learning_management.service;

import com.example.learning_management.dto.response.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponse enrollInCourse(Long userId, Long courseId);

    List<EnrollmentResponse> getAllEnrolledCourses(Long userId);

}

package com.example.learning_management.controller;

import com.example.learning_management.dto.request.EnrollmentRequest;
import com.example.learning_management.dto.response.ApiResponse;
import com.example.learning_management.dto.response.EnrollmentResponse;
import com.example.learning_management.security.CustomUserDetails;
import com.example.learning_management.service.EnrollmentService;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> enroll(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody EnrollmentRequest request) {

        Long userId = userDetails.getUser().getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Enrolled in course successfully", enrollmentService.enrollInCourse(userId, request.getCourseId())));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getMyEnrollments(@AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId();
        return ResponseEntity.ok(ApiResponse.success("Enrollments retrieved successfully", enrollmentService.getAllEnrolledCourses(userId)));
    }


}

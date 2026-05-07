package com.example.learning_management.controller;

import com.example.learning_management.dto.response.ApplicationResponse;
import com.example.learning_management.dto.response.CourseProgressResponse;
import com.example.learning_management.dto.response.GeneralResponse;
import com.example.learning_management.service.impl.LearningProgressImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learning-progress")
@RequiredArgsConstructor
public class LearningProgressController {

    private final LearningProgressImpl learningProgressService;

    @PostMapping("/{lectureId}/start")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> startLecture(@PathVariable Long lectureId) {

        GeneralResponse response = learningProgressService.startLecture(lectureId);
        return ResponseEntity   .status(HttpStatus.OK)
                .body(new ApplicationResponse<>(response));
    }

    @PostMapping("/{lectureId}/complete")
    public ResponseEntity<ApplicationResponse<GeneralResponse>> completeLecture(@PathVariable Long lectureId) {

        GeneralResponse response = learningProgressService.completeLecture(lectureId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApplicationResponse<>(response));
    }

    @GetMapping("/users/{userId}/course/{courseId}")
    public ResponseEntity<ApplicationResponse<CourseProgressResponse>> getProgressOfUser(@PathVariable Long userId, @PathVariable Long courseId) {

        CourseProgressResponse response = learningProgressService.getCourseProgress(userId, courseId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApplicationResponse<>(response));
    }
}

package com.example.learning_management.controller;

import com.example.learning_management.dto.response.ApiResponse;
import com.example.learning_management.dto.response.GeneralResponse;
import com.example.learning_management.service.impl.LearningProgressImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learning-progress")
@RequiredArgsConstructor
 public class LearningProgressController {

    private final LearningProgressImpl learningProgressService;

    @PostMapping("/{lectureId}/start")
     public ResponseEntity<ApiResponse<GeneralResponse>> startLecture(@PathVariable Long lectureId) {
        GeneralResponse response = learningProgressService.startLecture(lectureId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response.getMessage(), response));
    }

    @PostMapping("/{lectureId}/complete")
     public ResponseEntity<ApiResponse<GeneralResponse>> completeLecture(@PathVariable Long lectureId) {
        GeneralResponse response = learningProgressService.completeLecture(lectureId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(response.getMessage(), response));
    }
}

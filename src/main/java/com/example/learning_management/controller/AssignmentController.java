package com.example.learning_management.controller;

import com.example.learning_management.dto.request.AssignmentSubmissionRequest;
import com.example.learning_management.dto.request.GradeSubmissionRequest;
import com.example.learning_management.dto.request.SubmissionLookupRequest;
import com.example.learning_management.dto.response.ApplicationResponse;
import com.example.learning_management.dto.response.AssignmentSubmissionResponse;
import com.example.learning_management.security.CustomUserDetails;
import com.example.learning_management.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/submissions")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApplicationResponse<AssignmentSubmissionResponse>> submitAssignment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AssignmentSubmissionRequest request) {
        Long userId = userDetails.getUser().getId();
        AssignmentSubmissionResponse response = assignmentService.submitAssignment(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApplicationResponse<>(response));
    }

    @PatchMapping("/submissions/{submissionId}/grade")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationResponse<AssignmentSubmissionResponse>> gradeSubmission(
            @PathVariable Long submissionId,
            @Valid @RequestBody GradeSubmissionRequest request) {
        AssignmentSubmissionResponse response = assignmentService.gradeSubmission(submissionId, request);
        return ResponseEntity.ok(new ApplicationResponse<>(response));
    }

    @PostMapping("/submissions/my")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApplicationResponse<AssignmentSubmissionResponse>> getMySubmission(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody SubmissionLookupRequest request) {
        Long userId = userDetails.getUser().getId();
        AssignmentSubmissionResponse response = assignmentService.getSubmission(userId, request);
        return ResponseEntity.ok(new ApplicationResponse<>(response));
    }
}

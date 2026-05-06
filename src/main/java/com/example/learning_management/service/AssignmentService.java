package com.example.learning_management.service;

import com.example.learning_management.dto.request.AssignmentSubmissionRequest;
import com.example.learning_management.dto.request.GradeSubmissionRequest;
import com.example.learning_management.dto.request.SubmissionLookupRequest;
import com.example.learning_management.dto.response.AssignmentSubmissionResponse;

public interface AssignmentService {

    AssignmentSubmissionResponse submitAssignment(Long userId, AssignmentSubmissionRequest request);

    AssignmentSubmissionResponse gradeSubmission(Long submissionId, GradeSubmissionRequest request);

    AssignmentSubmissionResponse getSubmission(Long userId, SubmissionLookupRequest request);
}

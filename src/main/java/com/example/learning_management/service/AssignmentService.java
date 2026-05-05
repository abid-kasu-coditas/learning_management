package com.example.learning_management.service;

public interface AssignmentService {

    AssignmentSubmissionResponse submitAssignment(Long userId, Long assignmentId);

    AssignmentSubmissionResponse gradeSubmission(Long submissionId, Grade grade);

    AssignmentSubmissionResponse getSubmission(Long userId, Long assignmentId);
}

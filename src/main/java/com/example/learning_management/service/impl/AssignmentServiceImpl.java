package com.example.learning_management.service.impl;

import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.dto.request.AssignmentSubmissionRequest;
import com.example.learning_management.dto.request.GradeSubmissionRequest;
import com.example.learning_management.dto.request.SubmissionLookupRequest;
import com.example.learning_management.dto.response.AssignmentSubmissionResponse;
import com.example.learning_management.entitiy.Assignment;
import com.example.learning_management.entitiy.AssignmentSubmission;
import com.example.learning_management.entitiy.Course;
import com.example.learning_management.entitiy.Enrollment;
import com.example.learning_management.entitiy.User;
import com.example.learning_management.enums.EnrollmentStatus;
import com.example.learning_management.enums.Grade;
import com.example.learning_management.exception.AlreadyExistException;
import com.example.learning_management.exception.ForbiddenException;
import com.example.learning_management.exception.ResourceNotFoundException;
import com.example.learning_management.repository.AssignmentRepository;
import com.example.learning_management.repository.AssignmentSubmissionRepository;
import com.example.learning_management.repository.EnrollmentRepository;
import com.example.learning_management.repository.LearningProgressRepository;
import com.example.learning_management.repository.UserRepository;
import com.example.learning_management.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final AssignmentSubmissionRepository assignmentSubmissionRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final LearningProgressRepository learningProgressRepository;

    private final UserRepository userRepository;

    @Override
    public AssignmentSubmissionResponse submitAssignment(Long userId, AssignmentSubmissionRequest request) {

        User user = getUserByIdOrThrow(userId);
        Assignment assignment = getAssignmentByIdOrThrow(request.getAssignmentId());
        Course course = assignment.getCourse();

        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, course.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.ENROLLMENT_REQUIRED_FOR_ASSIGNMENT));

        validateCourseCompletion(userId, course, enrollment);

        assignmentSubmissionRepository.findByAssignmentAndUser(assignment, user)
                .ifPresent(existing -> {
                    throw new AlreadyExistException(ExceptionConstants.ASSIGNMENT_ALREADY_SUBMITTED);
                });

        AssignmentSubmission submission = AssignmentSubmission.builder()
                .assignment(assignment)
                .user(user)
                .build();

        AssignmentSubmission savedSubmission = assignmentSubmissionRepository.save(submission);
        return toResponse(savedSubmission);
    }

    @Override
    public AssignmentSubmissionResponse gradeSubmission(Long submissionId, GradeSubmissionRequest request) {

        AssignmentSubmission submission = assignmentSubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.SUBMISSION_NOT_FOUND));

        submission.setGrade(request.getGrade());
        submission.setPass(isPassingGrade(request.getGrade()));

        AssignmentSubmission savedSubmission = assignmentSubmissionRepository.save(submission);
        return toResponse(savedSubmission);
    }

    @Override
    public AssignmentSubmissionResponse getSubmission(Long userId, SubmissionLookupRequest request) {

        User user = getUserByIdOrThrow(userId);
        Assignment assignment = getAssignmentByIdOrThrow(request.getAssignmentId());

        AssignmentSubmission submission = assignmentSubmissionRepository.findByAssignmentAndUser(assignment, user)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.SUBMISSION_NOT_FOUND));

        return toResponse(submission);
    }

    private void validateCourseCompletion(Long userId, Course course, Enrollment enrollment) {

        if (enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
            return;
        }

        int totalLectures = course.getLectures() == null ? 0 : course.getLectures().size();
        long completedLectures = learningProgressRepository.countCompletedLectures(userId, course.getId());
        if (totalLectures == 0 || completedLectures < totalLectures) {
            throw new ForbiddenException(ExceptionConstants.COURSE_NOT_COMPLETED_FOR_SUBMISSION);
        }
    }

    private User getUserByIdOrThrow(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.USER_NOT_FOUND + userId));
    }

    private Assignment getAssignmentByIdOrThrow(Long assignmentId) {

        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.ASSIGNMENT_NOT_FOUND + assignmentId));
    }

    private AssignmentSubmissionResponse toResponse(AssignmentSubmission submission) {

        return AssignmentSubmissionResponse.builder()
                .submissionId(submission.getId())
                .assignmentId(submission.getAssignment().getId())
                .userId(submission.getUser().getId())
                .grade(submission.getGrade())
                .pass(submission.isPass())
                .build();
    }

    private boolean isPassingGrade(Grade grade) {

        return grade == Grade.A || grade == Grade.B || grade == Grade.C;
    }
}

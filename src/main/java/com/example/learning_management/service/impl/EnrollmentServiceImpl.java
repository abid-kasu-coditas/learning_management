package com.example.learning_management.service.impl;

import com.example.learning_management.dto.response.EnrollmentResponse;
import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.entitiy.Course;
import com.example.learning_management.entitiy.Enrollment;
import com.example.learning_management.entitiy.User;
import com.example.learning_management.enums.EnrollmentStatus;
import com.example.learning_management.exception.AlreadyExistException;
import com.example.learning_management.exception.ResourceNotFoundException;
import com.example.learning_management.mapper.EnrollmentMapper;
import com.example.learning_management.repository.CourseRepository;
import com.example.learning_management.repository.EnrollmentRepository;
import com.example.learning_management.repository.UserRepository;
import com.example.learning_management.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponse enrollInCourse(Long userId, Long courseId) {
        User user = getUserByIdOrThrow(userId);
        Course course = getCourseByIdOrThrow(courseId);

        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new AlreadyExistException(ExceptionConstants.ENROLLMENT_ALREADY_EXISTS);
        }

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .user(user)
                .status(EnrollmentStatus.ENROLLED)
                .build();
        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<EnrollmentResponse> getAllEnrolledCourses(Long userId) {
        return enrollmentMapper.toResponseList(enrollmentRepository.findByUserId(userId));
    }

    private User getUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.USER_NOT_FOUND + userId));
    }

    private Course getCourseByIdOrThrow(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.COURSE_NOT_FOUND + courseId));
    }
}

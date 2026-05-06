package com.example.learning_management.service.impl;

import com.example.learning_management.dto.response.CourseProgressResponse;
import com.example.learning_management.dto.response.GeneralResponse;
import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.entitiy.*;
import com.example.learning_management.enums.EnrollmentStatus;
import com.example.learning_management.exception.AuthenticationException;
import com.example.learning_management.exception.ResourceNotFoundException;
import com.example.learning_management.repository.*;
import com.example.learning_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LearningProgressImpl {

    private static final String LECTURE_STARTED_MESSAGE = "LECTURE_STARTED";

    private static final String LECTURE_COMPLETED_MESSAGE = "LECTURE_COMPLETED";

    private final LearningProgressRepository learningProgressRepository;

    private final LectureRepository lectureRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final AuthService authService;


    public GeneralResponse startLecture(Long lectureId) {

        Lecture lecture = getLectureByIdOrThrow(lectureId);
        User user = getAuthenticatedUser();

        LearningProgress progress = learningProgressRepository.findByLectureAndUser(lecture, user)
                .orElseGet(() -> LearningProgress.builder()
                        .lecture(lecture)
                        .user(user)
                        .build());

        progress.setCompleted(false);
        progress.setCompletedAt(null);
        learningProgressRepository.save(progress);

        return new GeneralResponse(LECTURE_STARTED_MESSAGE);
    }

    public GeneralResponse completeLecture(Long lectureId) {

        Lecture lecture = getLectureByIdOrThrow(lectureId);
        User user = getAuthenticatedUser();

        LearningProgress progress = learningProgressRepository.findByLectureAndUser(lecture, user)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.LEARNING_PROGRESS_NOT_FOUND));

        progress.setCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());
        learningProgressRepository.save(progress);

        return new GeneralResponse(LECTURE_COMPLETED_MESSAGE);
    }

    public CourseProgressResponse getCourseProgress(Long userId, Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.COURSE_NOT_FOUND + courseId));

        Long totalLectures = (long) (course.getLectures() == null ? 0 : course.getLectures().size());
        Long completedLectures = learningProgressRepository.countCompletedLectures(userId, courseId);

        Long completedPercentage = (completedLectures / totalLectures) * 100;

        return CourseProgressResponse.builder()
                .id(courseId)
                .courseTitle(course.getTitle())
                .totalLectures(totalLectures)
                .completedLectures(completedLectures)
                .completePercentage(completedPercentage)
                .enrollmentStatus(enrollmentRepository.findByUserIdAndCourseId(userId, courseId).get().getStatus())
                .build();


    }

    private Lecture getLectureByIdOrThrow(Long lectureId) {

        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.LECTURE_NOT_FOUND + lectureId));
    }

    private User getAuthenticatedUser() {

        String email = authService.getLoggedInEmployee();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(ExceptionConstants.USER_NOT_FOUND_EMAIL + email));
    }
}

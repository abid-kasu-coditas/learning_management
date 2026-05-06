package com.example.learning_management.service.impl;

import com.example.learning_management.dto.response.GeneralResponse;
import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.entitiy.LearningProgress;
import com.example.learning_management.entitiy.Lecture;
import com.example.learning_management.entitiy.User;
import com.example.learning_management.exception.AuthenticationException;
import com.example.learning_management.exception.ResourceNotFoundException;
import com.example.learning_management.repository.LearningProgressRepository;
import com.example.learning_management.repository.LectureRepository;
import com.example.learning_management.repository.UserRepository;
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

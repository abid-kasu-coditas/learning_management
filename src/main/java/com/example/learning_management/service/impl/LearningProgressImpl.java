package com.example.learning_management.service.impl;

import com.example.learning_management.dto.response.GeneralResponse;
import com.example.learning_management.entitiy.LearningProgress;
import com.example.learning_management.entitiy.Lecture;
import com.example.learning_management.entitiy.User;
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

    private final LearningProgressRepository learningProgressRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public GeneralResponse startLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("LECTURE_NOT_FOUND"));

        String email = authService.getLoggedInEmployee();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        LearningProgress progress = learningProgressRepository.findByLectureAndUser(lecture, user)
                .orElseGet(() -> LearningProgress.builder()
                        .lecture(lecture)
                        .user(user)
                        .build());

        progress.setCompleted(false);
        progress.setCompletedAt(null);
        learningProgressRepository.save(progress);

        return new GeneralResponse("LECTURE_STARTED");
    }

    public GeneralResponse completeLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("LECTURE_NOT_FOUND"));

        String email = authService.getLoggedInEmployee();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        LearningProgress progress = learningProgressRepository.findByLectureAndUser(lecture, user)
                .orElseThrow(() -> new RuntimeException("LECTURE_PROGRESS_NOT_FOUND"));

        progress.setCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());
        learningProgressRepository.save(progress);

        return new GeneralResponse("LECTURE_COMPLETED");
    }
}

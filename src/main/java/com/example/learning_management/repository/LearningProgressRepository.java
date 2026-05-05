package com.example.learning_management.repository;

import com.example.learning_management.entitiy.LearningProgress;
import com.example.learning_management.entitiy.Lecture;
import com.example.learning_management.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {


    Optional<LearningProgress> findByLectureAndUser(Lecture lecture, User user);
}

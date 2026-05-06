package com.example.learning_management.repository;

import com.example.learning_management.entitiy.LearningProgress;
import com.example.learning_management.entitiy.Lecture;
import com.example.learning_management.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {


    Optional<LearningProgress> findByLectureAndUser(Lecture lecture, User user);

    @Query("""
            select count(lp.id)
            from LearningProgress lp
            where lp.user.id = :userId
              and lp.lecture.course.id = :courseId
              and lp.completed = true
            """)
    long countCompletedLectures(Long userId, Long courseId);
}

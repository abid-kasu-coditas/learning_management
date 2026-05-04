package com.example.learning_management.repository;

import com.example.learning_management.entitiy.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningProgressRepository extends JpaRepository<LearningProgress,Long> {

}

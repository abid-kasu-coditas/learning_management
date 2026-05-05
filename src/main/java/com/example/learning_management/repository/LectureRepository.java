package com.example.learning_management.repository;

import com.example.learning_management.entitiy.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

}

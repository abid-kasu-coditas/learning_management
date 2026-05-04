package com.example.learning_management.repository;

import com.example.learning_management.entitiy.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {

}

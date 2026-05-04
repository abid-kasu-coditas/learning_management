package com.example.learning_management.repository;

import com.example.learning_management.entitiy.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

}

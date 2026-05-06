package com.example.learning_management.repository;

import com.example.learning_management.entitiy.Assignment;
import com.example.learning_management.entitiy.AssignmentSubmission;
import com.example.learning_management.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {

    Optional<AssignmentSubmission> findByAssignmentAndUser(Assignment assignment, User user);

}

package com.example.learning_management.repository;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

}

package com.example.learning_management.repository;

import com.example.learning_management.entitiy.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetails,Long> {

}

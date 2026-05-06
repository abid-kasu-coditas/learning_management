package com.example.learning_management.repository;

import com.example.learning_management.entitiy.EmployeeDetails;
import com.example.learning_management.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetails,Long> {

    List<EmployeeDetails> findBenchEmployeeEnrolled(@Param("staus") EmployeeStatus status)

}

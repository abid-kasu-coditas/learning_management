package com.example.learning_management.repository;

import com.example.learning_management.entitiy.EmployeeDetails;
import com.example.learning_management.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetails, Long> {

    @Query("""
            SELECT ed
            FROM EmployeeDetails ed
            WHERE ed.status = :status
              AND ed.user.id IN (
                    SELECT e.user.id
                    FROM Enrollment e
              )
            """)
    List<EmployeeDetails> findBenchEmployeesEnrolled(@Param("status") EmployeeStatus status);

    @Query("""
            SELECT ed
            FROM EmployeeDetails ed
            WHERE ed.status = :status
              AND ed.user.id NOT IN (
                    SELECT e.user.id
                    FROM Enrollment e
              )
            """)
    List<EmployeeDetails> findBenchEmployeesNotEnrolled(@Param("status") EmployeeStatus status);
}

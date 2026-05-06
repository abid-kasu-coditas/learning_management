package com.example.learning_management.dto.response;

import com.example.learning_management.enums.Department;
import com.example.learning_management.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProgressResponse {

    private Long userId;

    private String userName;

    private String email;

    private Department department;

    private EmployeeStatus employeeStatus;
    
    private int totalEnrollments;

    private int completedCourse;

    private int certificationIssued;

}

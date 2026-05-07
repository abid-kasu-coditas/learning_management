package com.example.learning_management.service;

import com.example.learning_management.entitiy.EmployeeDetails;

import java.util.List;

public interface AdminDashboardService {

    List<EmployeeDetails> getEmployeeOnBenchAndNotEnrolled();

    List<EmployeeDetails> getEmployeeOnBenchAndEnrolled();

    List<EmployeeDetails> getEmployeeOffBenchAndNotEnrolled();

    List<EmployeeDetails> getEmployeeOffBenchAndEnrolled();


}

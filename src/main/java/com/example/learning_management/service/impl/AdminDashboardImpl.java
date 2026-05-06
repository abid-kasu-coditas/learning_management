package com.example.learning_management.service.impl;

import com.example.learning_management.dto.response.EmployeeProgressResponse;
import com.example.learning_management.entitiy.EmployeeDetails;
import com.example.learning_management.repository.EmployeeDetailRepository;
import com.example.learning_management.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardImpl implements AdminDashboardService {

    private EmployeeDetailRepository employeeDetailRepository;

    @Override
    public List<EmployeeProgressResponse> getEmployeeOnBenchAndNotEnrolled() {


    }

    @Override
    public List<EmployeeProgressResponse> getEmployeeOnBenchAndEnrolled() {

    }

    @Override
    public List<EmployeeProgressResponse> getEmployeeOffBenchAndNotEnrolled() {

        return List.of();
    }

    @Override
    public List<EmployeeProgressResponse> getEmployeeOffBenchAndEnrolled() {

        return List.of();
    }
}

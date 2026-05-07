package com.example.learning_management.service.impl;

import com.example.learning_management.entitiy.EmployeeDetails;
import com.example.learning_management.enums.EmployeeStatus;
import com.example.learning_management.repository.EmployeeDetailRepository;
import com.example.learning_management.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardImpl implements AdminDashboardService {

    private final EmployeeDetailRepository employeeDetailRepository;

    @Override
    public List<EmployeeDetails> getEmployeeOnBenchAndNotEnrolled() {
        return employeeDetailRepository.findBenchEmployeesNotEnrolled(EmployeeStatus.ON_BENCH);
    }

    @Override
    public List<EmployeeDetails> getEmployeeOnBenchAndEnrolled() {
        return employeeDetailRepository.findBenchEmployeesEnrolled(EmployeeStatus.ON_BENCH);
    }

    @Override
    public List<EmployeeDetails> getEmployeeOffBenchAndNotEnrolled() {
        return employeeDetailRepository.findBenchEmployeesNotEnrolled(EmployeeStatus.ON_PROJECT);
    }

    @Override
    public List<EmployeeDetails> getEmployeeOffBenchAndEnrolled() {
        return employeeDetailRepository.findBenchEmployeesEnrolled(EmployeeStatus.ON_PROJECT);
    }
}

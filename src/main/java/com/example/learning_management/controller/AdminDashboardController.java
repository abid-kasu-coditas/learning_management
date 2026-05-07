package com.example.learning_management.controller;

import com.example.learning_management.dto.response.ApplicationResponse;
import com.example.learning_management.entitiy.EmployeeDetails;
import com.example.learning_management.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/bench/not-enrolled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationResponse<List<EmployeeDetails>>> getEmployeeOnBenchAndNotEnrolled() {
        List<EmployeeDetails> response = adminDashboardService.getEmployeeOnBenchAndNotEnrolled();
        return ResponseEntity.ok(new ApplicationResponse<>(response));
    }

    @GetMapping("/bench/enrolled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationResponse<List<EmployeeDetails>>> getEmployeeOnBenchAndEnrolled() {
        List<EmployeeDetails> response = adminDashboardService.getEmployeeOnBenchAndEnrolled();
        return ResponseEntity.ok(new ApplicationResponse<>(response));
    }

    @GetMapping("/off-bench/not-enrolled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationResponse<List<EmployeeDetails>>> getEmployeeOffBenchAndNotEnrolled() {
        List<EmployeeDetails> response = adminDashboardService.getEmployeeOffBenchAndNotEnrolled();
        return ResponseEntity.ok(new ApplicationResponse<>(response));
    }

    @GetMapping("/off-bench/enrolled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationResponse<List<EmployeeDetails>>> getEmployeeOffBenchAndEnrolled() {
        List<EmployeeDetails> response = adminDashboardService.getEmployeeOffBenchAndEnrolled();
        return ResponseEntity.ok(new ApplicationResponse<>(response));
    }
}

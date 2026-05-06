package com.example.learning_management.service;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         z
import com.example.learning_management.dto.response.EmployeeProgressResponse;

import java.util.List;

public interface AdminDashboardService {

    List<EmployeeProgressResponse> getEmployeeOnBenchAndNotEnrolled();

    List<EmployeeProgressResponse> getEmployeeOnBenchAndEnrolled();

    List<EmployeeProgressResponse> getEmployeeOffBenchAndNotEnrolled();

    List<EmployeeProgressResponse> getEmployeeOffBenchAndEnrolled();


}

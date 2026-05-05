package com.example.learning_management.controller;

import com.example.learning_management.dto.request.CreateCourseRequest;
import com.example.learning_management.dto.request.CreateLectureRequest;
import com.example.learning_management.dto.response.ApiResponse;
import com.example.learning_management.dto.response.CourseResponse;
import com.example.learning_management.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CreateCourseRequest createCourseRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Course Created Sucessfully", courseService.createCourse(createCourseRequest)));

    }

    @PostMapping("/lectures")
    public ResponseEntity<ApiResponse<CourseResponse>> addLectures(@Valid @RequestBody CreateLectureRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Course Added Sucessfully", courseService.addLecture(request)));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Courses Retrieved Sucessfully", courseService.getAllCourses()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Course Retrived Sucessfully", courseService.getCourseById(id)));
    }
}

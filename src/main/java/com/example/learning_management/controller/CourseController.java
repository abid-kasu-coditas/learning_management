package com.example.learning_management.controller;

import com.example.learning_management.dto.request.CreateCourseRequest;
import com.example.learning_management.dto.request.CreateLectureRequest;
import com.example.learning_management.dto.response.ApplicationResponse;
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
    public ResponseEntity<ApplicationResponse<CourseResponse>> createCourse(@Valid @RequestBody CreateCourseRequest createCourseRequest) {

        CourseResponse response = courseService.createCourse(createCourseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApplicationResponse<>(response));
    }

    @PostMapping("/lectures")
    public ResponseEntity<ApplicationResponse<CourseResponse>> addLectures(@Valid @RequestBody CreateLectureRequest request) {

        CourseResponse response = courseService.addLecture(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApplicationResponse<>(response));
    }

    @GetMapping
    public ResponseEntity<ApplicationResponse<List<CourseResponse>>> getAllCourses() {

        List<CourseResponse> response = courseService.getAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(new ApplicationResponse<>(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse<CourseResponse>> getCourseById(@PathVariable Long id) {

        CourseResponse response = courseService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApplicationResponse<>(response));
    }

}

package com.example.learning_management.service;

import com.example.learning_management.dto.request.CreateCourseRequest;
import com.example.learning_management.dto.request.CreateLectureRequest;
import com.example.learning_management.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CreateCourseRequest request);

    CourseResponse addLecture(CreateLectureRequest request);

    List<CourseResponse> getAllCourses();

    CourseResponse getCourseById(Long courseId);
}

package com.example.learning_management.service.impl;

import com.example.learning_management.dto.request.CreateCourseRequest;
import com.example.learning_management.dto.request.CreateLectureRequest;
import com.example.learning_management.dto.response.CourseResponse;
import com.example.learning_management.entitiy.Course;
import com.example.learning_management.entitiy.Lecture;
import com.example.learning_management.mapper.CourseMapper;
import com.example.learning_management.repository.CourseRepository;
import com.example.learning_management.repository.LectureRepository;
import com.example.learning_management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final LectureRepository lectureRepository;

    private final CourseMapper courseMapper;


    @Override
    public CourseResponse createCourse(CreateCourseRequest request) {

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse addLecture(CreateLectureRequest request) {

        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new RuntimeException("ADD EXCEPTION HERE"));
        Lecture lecture = Lecture.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .resourceType(request.getResourceType())
                .course(course)
                .build();
        lectureRepository.save(lecture);

        course.getLectures().add(lecture);
        return courseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {

        return courseMapper.toResponseList(courseRepository.findAll());

    }

    @Override
    public CourseResponse getCourseById(Long courseId) {

        return courseRepository.findById(courseId).map(courseMapper::toResponse).orElseThrow(() -> new RuntimeException());
    }
}

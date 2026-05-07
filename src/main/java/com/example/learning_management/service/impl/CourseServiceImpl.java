package com.example.learning_management.service.impl;

import com.example.learning_management.dto.request.CreateCourseRequest;
import com.example.learning_management.dto.request.CreateLectureRequest;
import com.example.learning_management.dto.response.CourseResponse;
import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.entitiy.Course;
import com.example.learning_management.entitiy.Lecture;
import com.example.learning_management.exception.ResourceNotFoundException;
import com.example.learning_management.mapper.CourseMapper;
import com.example.learning_management.repository.CourseRepository;
import com.example.learning_management.repository.LectureRepository;
import com.example.learning_management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final LectureRepository lectureRepository;

    private final CourseMapper courseMapper;


    @Override
    public CourseResponse createCourse(CreateCourseRequest request) {
        Course courseToSave = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        return courseMapper.toResponse(courseRepository.save(courseToSave));
    }

    @Override
    public CourseResponse addLecture(CreateLectureRequest request) {
        Course course =courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.COURSE_NOT_FOUND + request.getCourseId()));;

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
    public List<CourseResponse> getAllCourses(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<Course> coursePage = courseRepository.findAll(pageable);

        return courseMapper.toResponseList(coursePage.getContent());
     }

    @Override
    public CourseResponse getCourseById(Long courseId) {
      Course course =  courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.COURSE_NOT_FOUND + courseId));
        return courseMapper.toResponse(course);
    }

}

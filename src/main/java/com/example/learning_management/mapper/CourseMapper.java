package com.example.learning_management.mapper;

import com.example.learning_management.dto.response.CourseResponse;
import com.example.learning_management.entitiy.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "totalLectures", expression = "java(course.getLectures().size())")
    CourseResponse toResponse(Course course);

    List<CourseResponse> toResponseList(List<Course> courses);
}

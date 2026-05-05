package com.example.learning_management.mapper;

import com.example.learning_management.dto.response.EnrollmentResponse;
import com.example.learning_management.entitiy.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseTitle")
    EnrollmentResponse toResponse(Enrollment enrollment);


    List<EnrollmentResponse> toResponseList(List<Enrollment> enrollments);

}

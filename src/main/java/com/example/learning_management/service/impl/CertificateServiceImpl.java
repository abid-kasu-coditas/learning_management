package com.example.learning_management.service.impl;

import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.dto.response.CertificateResponse;
import com.example.learning_management.dto.response.CourseResponse;
import com.example.learning_management.entitiy.AssignmentSubmission;
import com.example.learning_management.entitiy.Certificate;
import com.example.learning_management.entitiy.Course;
import com.example.learning_management.entitiy.User;
import com.example.learning_management.exception.ResourceNotFoundException;
import com.example.learning_management.mapper.CertificateMapper;
import com.example.learning_management.mapper.CourseMapper;
import com.example.learning_management.repository.*;
import com.example.learning_management.service.CertificateService;

import java.time.LocalDate;
import java.util.List;

public class CertificateServiceImpl implements CertificateService {

    private UserRepository userRepository;

    private CourseRepository courseRepository;

    private CertificateRepository certificateRepository;

    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    private CertificateMapper certificateMapper;

    @Override
    public CertificateResponse issueCertificate(Long userId, Long courseId) {

        AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.ASSIGNMENT_NOT_FOUND + "FOR THE SUBMISSION"));

        if (!assignmentSubmission.isPass()) {
            throw new ResourceNotFoundException(ExceptionConstants.NOT_PASSED_ASSIGNMENT);
        }

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException(ExceptionConstants.COURSE_NOT_FOUND));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ExceptionConstants.USER_NOT_FOUND));


        Certificate certificate = Certificate.builder()
                .user(user)
                .course(course)
                .issuedAt(LocalDate.now())
                .build();

        return certificateMapper.toResponse(certificate);
    }

    @Override
    public List<CertificateResponse> getCertificateByUser(Long userId) {

        List<Certificate> certificate = certificateRepository.findByUserId(userId);

        return certificateMapper.toResponseList(certificate);
    }

    @Override
    public List<CertificateResponse> getCertificateByCourse(Long courseId) {

        List<Certificate> certificates = certificateRepository.findByCourseId(courseId);

        return certificateMapper.toResponseList(certificates);

    }

}

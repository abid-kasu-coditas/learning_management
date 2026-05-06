package com.example.learning_management.service;

import com.example.learning_management.dto.response.CertificateResponse;

import java.util.List;

public interface CertificateService {

    CertificateResponse issueCertificate(Long userId, Long courseId);

    List<CertificateResponse> getCertificateByUser(Long userId);

    List<CertificateResponse> getCertificateByCourse(Long courseId);
}

package com.example.learning_management.repository;

import com.example.learning_management.entitiy.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByUserId(Long userid);

    List<Certificate> findByCourseId(Long courseId);
}

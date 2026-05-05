package com.example.learning_management.entitiy;

import com.example.learning_management.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @Column(name = "enrolled_at",nullable = false,updatable = false)
    @Builder.Default
    private LocalDateTime enrolledAt = LocalDateTime.now();

}

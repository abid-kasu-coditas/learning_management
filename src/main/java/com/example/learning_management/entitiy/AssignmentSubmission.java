package com.example.learning_management.entitiy;

import com.example.learning_management.enums.Grade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assignment_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "pass_status")
    @Builder.Default
    boolean pass = false;

}

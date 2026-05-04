package com.example.learning_management.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "problem_statement")
    private String problemStatement;

    @OneToOne
    private Course course;

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL)
    @Builder.Default
    private List<AssignmentSubmission> assignmentSubmissions = new ArrayList<>();


}

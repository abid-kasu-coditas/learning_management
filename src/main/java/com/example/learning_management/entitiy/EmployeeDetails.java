package com.example.learning_management.entitiy;

import com.example.learning_management.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",unique = true)
    private User user;

    @Column(name = "department",nullable = false)
    private String department;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

}

package com.example.learning_management.entitiy;


import com.example.learning_management.enums.Department;
import com.example.learning_management.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_details")
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
    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;




}

package com.example.learning_management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentSubmissionRequest {

    @NotNull
    private Long assignmentId;
}

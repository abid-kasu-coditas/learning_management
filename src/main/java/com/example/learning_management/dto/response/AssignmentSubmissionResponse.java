package com.example.learning_management.dto.response;

import com.example.learning_management.enums.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssignmentSubmissionResponse {

    private Long submissionId;
    private Long assignmentId;
    private Long userId;
    private Grade grade;
    private boolean pass;
}

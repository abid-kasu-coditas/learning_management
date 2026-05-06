package com.example.learning_management.dto.request;

import com.example.learning_management.enums.Grade;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeSubmissionRequest {

    @NotNull
    private Grade grade;
}

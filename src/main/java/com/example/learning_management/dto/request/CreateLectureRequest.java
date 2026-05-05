package com.example.learning_management.dto.request;

import com.example.learning_management.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLectureRequest {

    private String title;

    private String description;

    private ResourceType resourceType;

    private Long courseId;
}

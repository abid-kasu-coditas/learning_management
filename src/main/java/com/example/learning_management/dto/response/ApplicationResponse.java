package com.example.learning_management.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationResponse<T> {

    private T data;
    private List<ErrorResponse> errors;

    public ApplicationResponse(T data) {
        this.data = data;
    }

    public ApplicationResponse(List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public ApplicationResponse(T data, List<ErrorResponse> errors) {
        this.data = data;
        this.errors = errors;
    }
}

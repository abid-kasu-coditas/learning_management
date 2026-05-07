package com.example.learning_management.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationResponse<T> {

    private T data;

    public ApplicationResponse(T data) {
        this.data = data;
    }


}

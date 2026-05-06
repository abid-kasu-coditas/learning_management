package com.example.learning_management.exception;

import com.example.learning_management.constants.ExceptionConstants;
import com.example.learning_management.dto.response.ApplicationResponse;
import com.example.learning_management.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleNotFound(ResourceNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleAlreadyExist(AlreadyExistException ex) {
        return errorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleAuthentication(AuthenticationException ex) {
        return errorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleAuthorization(AuthorizationException ex) {
        return errorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleForbidden(ForbiddenException ex) {
        return errorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse(
                        error.getField() + ": " + error.getDefaultMessage(),
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value()))
                .toList();

        return ResponseEntity.badRequest().body(new ApplicationResponse<>(errors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleBadRequest(IllegalArgumentException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleIllegalState(IllegalStateException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationResponse<List<ErrorResponse>>> handleGeneric(Exception ex) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionConstants.UNEXPECTED_SERVER_ERROR);
    }

    private ResponseEntity<ApplicationResponse<List<ErrorResponse>>> errorResponse(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(message, LocalDateTime.now(), status.value());
        ApplicationResponse<List<ErrorResponse>> body = new ApplicationResponse<>(List.of(error));
        return ResponseEntity.status(status).body(body);
    }

}

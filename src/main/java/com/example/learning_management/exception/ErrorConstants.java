package com.example.learning_management.exception;

public final class ErrorConstants {

    private ErrorConstants(){

    }

    public static final String EMAIL_ALREADY_EXISTS = "Email is already registered";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found";
    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token has expired. Please login again";

    public static final String USER_NOT_FOUND = "User not found with id: ";
    public static final String USER_NOT_FOUND_EMAIL = "User not found with email: ";

}

package com.example.learning_management.constants;

public final class ExceptionConstants {

    private ExceptionConstants() {

    }

    public static final String USER_NOT_FOUND = "User not found with id: ";

    public static final String USER_NOT_FOUND_EMAIL = "User not found with email: ";

    public static final String USER_ALREADY_EXISTS_EMAIL = "User already exists with email: ";

    public static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found";

    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token has expired";

    public static final String COURSE_NOT_FOUND = "Course not found with id: ";

    public static final String LECTURE_NOT_FOUND = "Lecture not found with id: ";

    public static final String ASSIGNMENT_NOT_FOUND = "Assignment not found with id: ";

    public static final String SUBMISSION_NOT_FOUND = "Assignment submission not found";

    public static final String ENROLLMENT_ALREADY_EXISTS = "User is already enrolled in this course";

    public static final String ENROLLMENT_REQUIRED_FOR_ASSIGNMENT = "Enrollment is required to access assignment";

    public static final String COURSE_NOT_COMPLETED_FOR_SUBMISSION = "Complete course lectures before submitting assignment";

    public static final String ASSIGNMENT_ALREADY_SUBMITTED = "Assignment already submitted";

    public static final String LEARNING_PROGRESS_NOT_FOUND = "Learning progress not found for lecture";

    public static final String UNEXPECTED_SERVER_ERROR = "Unexpected server error";

    public static final String NOT_PASSED_ASSIGNMENT = "Sorry you'have not passed the assignment";

    public static final String NOT_CORRECT_BODY = "please provide requestbody";
}

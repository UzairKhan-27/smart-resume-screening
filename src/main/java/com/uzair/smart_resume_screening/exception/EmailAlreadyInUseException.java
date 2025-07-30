package com.uzair.smart_resume_screening.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super("Email "+email+" already in use");
    }
}

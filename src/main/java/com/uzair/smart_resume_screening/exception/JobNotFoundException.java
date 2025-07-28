package com.uzair.smart_resume_screening.exception;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(int id) {
        super("Job with id "+id+" not found");
    }
}

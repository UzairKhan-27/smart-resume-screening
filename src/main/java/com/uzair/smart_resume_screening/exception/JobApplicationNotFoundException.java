package com.uzair.smart_resume_screening.exception;

public class JobApplicationNotFoundException extends RuntimeException {
    public JobApplicationNotFoundException(int jobId,int personId) {
        super("Job Application Not Found");
    }
}

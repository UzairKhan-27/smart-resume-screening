package com.uzair.smart_resume_screening.dto;

public record JobApplicationResponse(JobResponse job, PersonResponse person,
                                     ResumeResponse resume) {
}

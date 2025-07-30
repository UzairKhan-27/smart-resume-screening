package com.uzair.smart_resume_screening.dto;

import com.uzair.smart_resume_screening.model.Response;

public record JobApplicationResponse(JobResponse job, PersonResponse person,
                                     ResumeResponse resume, Response response) {
}

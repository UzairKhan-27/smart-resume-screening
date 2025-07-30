package com.uzair.smart_resume_screening.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateJobApplicationRequest(@NotNull @Min(1) int job_id, @NotNull @Min(1) int person_id) {
}

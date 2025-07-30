package com.uzair.smart_resume_screening.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateJobRequest(@NotEmpty String description, @NotEmpty String role) {
}

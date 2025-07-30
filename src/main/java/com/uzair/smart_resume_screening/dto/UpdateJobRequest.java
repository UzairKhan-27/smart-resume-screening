package com.uzair.smart_resume_screening.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateJobRequest(@NotEmpty String description, @NotEmpty String role) {
}

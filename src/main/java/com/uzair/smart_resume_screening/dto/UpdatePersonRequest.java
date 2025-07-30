package com.uzair.smart_resume_screening.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePersonRequest(@NotBlank String email) {
}

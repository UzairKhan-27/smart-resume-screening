package com.uzair.smart_resume_screening.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreatePersonRequest(@NotEmpty String email ) {
}

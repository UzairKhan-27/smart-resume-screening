package com.uzair.smart_resume_screening.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzair.smart_resume_screening.dto.ResumeEvaluationResponse;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ResumeEvaluationConverter implements AttributeConverter<ResumeEvaluationResponse,String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(ResumeEvaluationResponse resumeEvaluationResponse) {
        try {
            return objectMapper.writeValueAsString(resumeEvaluationResponse);
        } catch (JsonProcessingException jpe) {
            return null;
        }
    }

    @Override
    public ResumeEvaluationResponse convertToEntityAttribute(String value) {
        try {
            return objectMapper.readValue(value, ResumeEvaluationResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}

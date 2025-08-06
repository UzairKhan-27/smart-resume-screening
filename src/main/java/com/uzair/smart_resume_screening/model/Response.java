package com.uzair.smart_resume_screening.model;

import com.uzair.smart_resume_screening.dto.ResumeEvaluationResponse;
import com.uzair.smart_resume_screening.service.ResumeEvaluationConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;
    @Convert(converter = ResumeEvaluationConverter.class)
    @Column(columnDefinition = "TEXT")
    private ResumeEvaluationResponse resumeEvaluationResponse;
}

package com.uzair.smart_resume_screening.dto;

import lombok.Data;

import java.util.List;
@Data

public class ResumeEvaluationResponse {

    public List<Education> education;
    public List<Experience> experiences;
    public OverallEvaluation overallEvaluation;

    public static class Education {
        public String degree;
        public String institution;
        public String evaluation;
        public int score;
    }

    public static class Experience {
        public String role;
        public String company;
        public String evaluation;
        public int score;
    }

    public static class OverallEvaluation {
        public String summary;
        public int score;
    }

}

package com.uzair.smart_resume_screening.service;

import com.google.api.client.json.Json;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.uzair.smart_resume_screening.dto.JobResponse;
import com.uzair.smart_resume_screening.dto.ResumeResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    public String askGemini(ResumeResponse resumeResponse, JobResponse jobResponse) {
        Client client = new Client();
        String generalPrompt = """
You are an AI system designed to evaluate professional resumes.
For each education listed in the resume:
- Provide a brief evaluation of the education (e.g., relevance to the role, prestige of institution, field alignment, etc.).
- Assign a score from 0 to 10 based on factors like degree level, relevance, and institution reputation.
For each experience listed in the resume:
- Analyze the relevance, clarity, and impact of the experience.
- Provide a brief evaluation and assign a score from 1 to 10.
- Do not match the relevancy of experience against job description.
After reviewing all experiences:
- Provide an overall rating for the candidate’s resume on a scale from 1 to 10 according to the analyzed experience along with job description.
- Justify the overall score based on the quality, alignment with expected roles, and completeness of the resume.

-If uploaded file is not resume throw error in the format:
{
    "error":[brief description of error]
}
Be objective, consistent, and concise.
DO NOT MAKE ASSUMPTIONS.
NEVER PRINT JSON MARKDOWN LIKE ```json.
""";
        String expectedOutput = """
BELOW IS THE EXPECTED OUTPUT STICK TO JUS THIS
NEVER PRINT JSON MARKDOWN LIKE ```json
{
  "education": [
    {
      "degree": "[Degree or Qualification]",
      "institution": "[University or School Name]",
      "evaluation": "[Short analysis]",
      "score": 0
    },
    {
      "degree": "[Degree or Qualification]",
      "institution": "[University or School Name]",
      "evaluation": "...",
      "score": 0
    }
  ],
  "experiences": [
    {
      "role": "[Job Title / Role]",
      "company": "[Company Name]",
      "evaluation": "[Short analysis]",
      "score": 0
    },
    {
      "role": "[Job Title / Role]",
      "company": "[Company Name]",
      "evaluation": "...",
      "score": 0
    }
  ],
  "overall_evaluation": {
    "summary": "[Concise summary of resume quality]",
    "score": 0
  }
}
NEVER PRINT JSON MARKDOWN LIKE ```json
""";

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        generalPrompt+" "+jobResponse+" "+expectedOutput+" "+resumeResponse,
                        null);
        System.out.println(response.text());
        return response.text();
    }

}

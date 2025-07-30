package com.uzair.smart_resume_screening.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzair.smart_resume_screening.dto.*;
import com.uzair.smart_resume_screening.exception.JobApplicationNotFoundException;
import com.uzair.smart_resume_screening.exception.JobNotFoundException;
import com.uzair.smart_resume_screening.exception.NonResumeFileUploadedException;
import com.uzair.smart_resume_screening.exception.PersonNotFoundException;
import com.uzair.smart_resume_screening.mapper.JobMapper;
import com.uzair.smart_resume_screening.mapper.JobPersonMapper;
import com.uzair.smart_resume_screening.mapper.ResumeMapper;
import com.uzair.smart_resume_screening.model.*;
import com.uzair.smart_resume_screening.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobRepo jobRepo;
    private final PersonRepo personRepo;
    private final JobPersonRepo jobPersonRepo;
    private final ResumeService resumeService;
    private final GeminiService geminiService;
    private final ResumeMapper resumeMapper;
    private final JobMapper jobMapper;
    private final JobPersonMapper jobPersonMapper;

    public List<JobApplicationResponse> getAllJobApplications() {
        return jobPersonMapper.toDto(jobPersonRepo.findAll());
    }

    public ResumeEvaluationResponse createJobApplication(MultipartFile file, CreateJobApplicationRequest dto)
            throws IOException {
        Resume resume = resumeService.parseResume(file);
        Person person = getPerson(dto.person_id());
        Job job = getJob(dto.job_id());
        JobPersonPK jobPersonPK = setJobPersonCompositeKey(dto.person_id(),dto.job_id());
        ResumeEvaluationResponse resumeEvaluationResponse = getGeminiResponse(resume,job);
        saveJobPerson(jobPersonPK,job,person,resume,resumeEvaluationResponse);
        return resumeEvaluationResponse;
    }

    private Person getPerson(int personId){
        return personRepo.findById(personId).orElseThrow(()-> new PersonNotFoundException(personId));
    }
    private Job getJob(int jobId) {
        return jobRepo.findById(jobId).orElseThrow(()-> new JobNotFoundException(jobId));
    }
    private JobPersonPK setJobPersonCompositeKey(int personId,int jobId){
        JobPersonPK jobPersonPK = new JobPersonPK();
        jobPersonPK.setPerson_id(personId);
        jobPersonPK.setJob_id(jobId);
        return jobPersonPK;
    }
    private ResumeEvaluationResponse getGeminiResponse(Resume resume, Job job) throws JsonProcessingException {
        ResumeResponse resumeResponse = resumeMapper.toDto(resume);
        JobResponse jobResponse = jobMapper.toDto(job);
        String response = geminiService.askGemini(resumeResponse,jobResponse);
        if(!response.contains("overall_evaluation")){
            throw new NonResumeFileUploadedException("Uploaded File Not A Resume");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, ResumeEvaluationResponse.class);
    }
    private void saveJobPerson(JobPersonPK jobPersonPK, Job job, Person person,
                               Resume resume, ResumeEvaluationResponse resumeEvaluationResponse){
        JobPerson jobPerson = new JobPerson();
        jobPerson.setId(jobPersonPK);
        jobPerson.setPerson(person);
        jobPerson.setJob(job);
        jobPerson.setResume(resume);

        Response response = new Response();
        response.setResumeEvaluationResponse(resumeEvaluationResponse);
        jobPerson.setResponse(response);
        jobPersonRepo.save(jobPerson);
    }

    public JobApplicationResponse getJobApplication(int jobId, int personId) {
        JobPerson jobPerson = jobPersonRepo.findByJobIdAndPersonId(jobId, personId).orElseThrow(() ->
                new JobApplicationNotFoundException(jobId,personId));
        return jobPersonMapper.toDto(jobPerson);
    }

    public List<JobApplicationResponse> getJobApplicationsByJob(int jobId) {
        List<JobPerson> jobPersons = jobPersonRepo.findByJobId(jobId);
        if(jobPersons.isEmpty()){
            throw new JobApplicationNotFoundException(jobId,1);
        }
        return jobPersonMapper.toDto(jobPersons);
    }

    public String deleteJobApplication(int jobId, int personId) {
        jobPersonRepo.findByJobIdAndPersonId(jobId, personId).orElseThrow(() ->
                new JobApplicationNotFoundException(jobId,personId));
        jobPersonRepo.deleteByJobIdAndPersonId(jobId,personId);
        return "Deleted";
    }

    public String deleteJobApplicationsByJob(int jobId) {
        List<JobPerson> jobPersons = jobPersonRepo.findByJobId(jobId);
        if(jobPersons.isEmpty()){
            throw new JobApplicationNotFoundException(jobId,1);
        }
        jobPersonRepo.deleteAllByJobId(jobId);
        return "Deleted";
    }

    public String deleteJobApplicationsByPerson(int personId) {
        List<JobPerson> jobPersons = jobPersonRepo.findByPersonId(personId);
        if(jobPersons.isEmpty()){
            throw new JobApplicationNotFoundException(1,personId);
        }
        jobPersonRepo.deleteAllByPersonId(personId);
        return "Deleted";
    }
}

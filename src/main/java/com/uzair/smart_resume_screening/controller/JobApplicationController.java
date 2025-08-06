package com.uzair.smart_resume_screening.controller;

import com.uzair.smart_resume_screening.dto.CreateJobApplicationRequest;
import com.uzair.smart_resume_screening.dto.JobApplicationResponse;
import com.uzair.smart_resume_screening.dto.ResumeEvaluationResponse;
import com.uzair.smart_resume_screening.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-applications")
public class JobApplicationController {
    private final JobApplicationService service;

    @GetMapping
    public ResponseEntity<List<JobApplicationResponse>> getAllJobApplications(){
        return ResponseEntity.ok(service.getAllJobApplications());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeEvaluationResponse> createJobApplication(@RequestPart("file") MultipartFile file,
                                                                         @RequestPart("data")
                                                                         @Valid CreateJobApplicationRequest dto)
            throws IOException {
        return ResponseEntity.ok(service.createJobApplication(file,dto));
    }

    @GetMapping("/jobs/{jobId}/persons/{personId}")
    public ResponseEntity<JobApplicationResponse> getJobApplication(@PathVariable int jobId,
                                                                    @PathVariable int personId){
        return ResponseEntity.ok(service.getJobApplication(jobId,personId));
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Page<JobApplicationResponse>> getJobApplicationsByJob(@PathVariable int jobId,
                                                                                Pageable pageable)
    {
        return ResponseEntity.ok(service.getJobApplicationsByJob(jobId,pageable));
    }

    @DeleteMapping("/jobs/{jobId}/persons/{personId}")
    public ResponseEntity<String> deleteJobApplication(@PathVariable int jobId,
                                                       @PathVariable int personId){
        return ResponseEntity.ok(service.deleteJobApplication(jobId,personId));
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJobApplicationsByJob(@PathVariable int jobId){
        return ResponseEntity.ok(service.deleteJobApplicationsByJob(jobId));
    }

    @DeleteMapping("/persons/{personId}")
    public ResponseEntity<String> deleteJobApplicationsByPerson(@PathVariable int personId){
        return ResponseEntity.ok(service.deleteJobApplicationsByPerson(personId));
    }

}

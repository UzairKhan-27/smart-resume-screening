package com.uzair.smart_resume_screening.controller;

import com.uzair.smart_resume_screening.dto.CreateJobApplicationRequest;
import com.uzair.smart_resume_screening.dto.JobApplicationResponse;
import com.uzair.smart_resume_screening.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService service;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JobApplicationResponse> createJobApplication(@RequestPart("file") MultipartFile file,
                                                                       @RequestPart("data")
                                                                       CreateJobApplicationRequest dto)
            throws IOException {
        return ResponseEntity.ok(service.createJobApplication(file,dto));
    }
}

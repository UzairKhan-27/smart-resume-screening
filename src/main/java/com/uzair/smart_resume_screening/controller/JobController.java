package com.uzair.smart_resume_screening.controller;

import com.uzair.smart_resume_screening.dto.CreateJobRequest;
import com.uzair.smart_resume_screening.dto.JobResponse;
import com.uzair.smart_resume_screening.dto.UpdateJobRequest;
import com.uzair.smart_resume_screening.mapper.JobMapper;
import com.uzair.smart_resume_screening.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService service;
    @GetMapping
    public ResponseEntity<Page<JobResponse>> getAllJobs(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(service.getAllJobs(page,size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable int id){
        return ResponseEntity.ok(service.getJob(id));
    }
    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody CreateJobRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createJob(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable int id, @Valid @RequestBody UpdateJobRequest dto){
        return ResponseEntity.ok(service.updateJob(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id){
        return service.deleteJob(id);
    }


}

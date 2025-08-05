package com.uzair.smart_resume_screening.service;

import com.uzair.smart_resume_screening.dto.CreateJobRequest;
import com.uzair.smart_resume_screening.dto.JobResponse;
import com.uzair.smart_resume_screening.dto.UpdateJobRequest;
import com.uzair.smart_resume_screening.exception.JobApplicationNotFoundException;
import com.uzair.smart_resume_screening.exception.JobNotFoundException;
import com.uzair.smart_resume_screening.mapper.JobMapper;
import com.uzair.smart_resume_screening.model.Job;
import com.uzair.smart_resume_screening.model.JobPerson;
import com.uzair.smart_resume_screening.repo.JobPersonRepo;
import com.uzair.smart_resume_screening.repo.JobRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepo repo;
    private final JobMapper mapper;
    private final JobPersonRepo jobPersonRepo;

    public Page<JobResponse> getAllJobs(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Job> jobs = repo.findAll(pageable);
        return jobs.map(mapper::toDto);
    }

    public JobResponse getJob(int id) {
        Job job = repo.findById(id).orElseThrow(()-> new JobNotFoundException(id));
        return mapper.toDto(job);
    }

    public JobResponse createJob(CreateJobRequest dto) {
        Job job = mapper.toEntity(dto);
        return mapper.toDto(repo.save(job));
    }

    public JobResponse updateJob(int id, UpdateJobRequest dto){
        Job job = repo.findById(id).orElseThrow(()-> new JobNotFoundException(id));
        mapper.update(job,dto);
        return mapper.toDto(repo.save(job));
    }

    public ResponseEntity<String> deleteJob(int id) {
        Job job = repo.findById(id).orElseThrow(()-> new JobNotFoundException(id));
        List<JobPerson> jobPersons = jobPersonRepo.findByJobId(id);
        if(!jobPersons.isEmpty()){
            jobPersonRepo.deleteAllByJobId(id);
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Job Deleted");
    }
}

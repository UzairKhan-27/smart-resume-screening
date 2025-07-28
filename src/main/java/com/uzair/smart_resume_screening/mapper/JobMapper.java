package com.uzair.smart_resume_screening.mapper;

import com.uzair.smart_resume_screening.dto.CreateJobRequest;
import com.uzair.smart_resume_screening.dto.JobResponse;
import com.uzair.smart_resume_screening.dto.UpdateJobRequest;
import com.uzair.smart_resume_screening.model.Job;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {
    List<JobResponse> toDto(List<Job> jobs);
    JobResponse toDto(Job job);
    Job toEntity(CreateJobRequest createJobRequest);
    void update(@MappingTarget Job job, UpdateJobRequest updateJobRequest);
}

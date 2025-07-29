package com.uzair.smart_resume_screening.mapper;

import com.uzair.smart_resume_screening.dto.*;
import com.uzair.smart_resume_screening.model.Job;
import com.uzair.smart_resume_screening.model.JobPerson;
import com.uzair.smart_resume_screening.model.Person;
import com.uzair.smart_resume_screening.model.Resume;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {JobMapper.class, ResumeMapper.class, PersonMapper.class})
public interface JobPersonMapper {
    JobPerson toEntity(CreateJobApplicationRequest createJobApplicationRequest);
    JobApplicationResponse toDto(JobPerson jobPerson);
}

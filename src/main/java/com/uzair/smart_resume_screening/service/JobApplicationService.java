package com.uzair.smart_resume_screening.service;

import com.uzair.smart_resume_screening.dto.CreateJobApplicationRequest;
import com.uzair.smart_resume_screening.dto.JobApplicationResponse;
import com.uzair.smart_resume_screening.exception.JobNotFoundException;
import com.uzair.smart_resume_screening.exception.PersonNotFoundException;
import com.uzair.smart_resume_screening.mapper.JobPersonMapper;
import com.uzair.smart_resume_screening.model.*;
import com.uzair.smart_resume_screening.repo.JobPersonRepo;
import com.uzair.smart_resume_screening.repo.JobRepo;
import com.uzair.smart_resume_screening.repo.PersonRepo;
import com.uzair.smart_resume_screening.repo.ResumeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobRepo jobRepo;
    private final PersonRepo personRepo;
    private final ResumeRepo resumeRepo;
    private final JobPersonRepo jobPersonRepo;
    private final ResumeService resumeService;
    private final JobPersonMapper mapper;

    public JobApplicationResponse createJobApplication(MultipartFile file, CreateJobApplicationRequest dto)
            throws IOException {
        JobPerson jobPerson = new JobPerson();
        Resume resume = resumeService.parseResume(file);
        Person person = personRepo.findById(dto.person_id()).orElseThrow(()->
                new PersonNotFoundException(dto.person_id()));
        Job job = jobRepo.findById(dto.job_id()).orElseThrow(()-> new JobNotFoundException(dto.job_id()));
        JobPersonPK jobPersonPK = new JobPersonPK();
        jobPersonPK.setPerson_id(person.getId());
        jobPersonPK.setJob_id(job.getId());
        jobPerson.setId(jobPersonPK);
        jobPerson.setPerson(person);
        jobPerson.setResume(resume);
        jobPerson.setJob(job);
        return mapper.toDto(jobPersonRepo.save(jobPerson));

    }
}

package com.uzair.smart_resume_screening.mapper;

import com.uzair.smart_resume_screening.dto.ResumeResponse;
import com.uzair.smart_resume_screening.model.Resume;
import org.mapstruct.Mapper;

import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeResponse toDto(Resume resume);

    Resume toEntity(HashMap<String, String> resumeSectionsContent);
}

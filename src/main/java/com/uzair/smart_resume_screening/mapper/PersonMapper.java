package com.uzair.smart_resume_screening.mapper;

import com.uzair.smart_resume_screening.dto.CreatePersonRequest;
import com.uzair.smart_resume_screening.dto.PersonResponse;
import com.uzair.smart_resume_screening.dto.UpdatePersonRequest;
import com.uzair.smart_resume_screening.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    List<PersonResponse> toDto(List<Person> persons);
    PersonResponse toDto(Person person);
    Person toEntity(CreatePersonRequest createPersonRequest);
    Person toEntity(UpdatePersonRequest updatePersonRequest);
    void update(@MappingTarget Person person, UpdatePersonRequest updatePersonRequest);

}

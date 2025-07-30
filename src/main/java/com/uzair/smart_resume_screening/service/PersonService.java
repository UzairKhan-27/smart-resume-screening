package com.uzair.smart_resume_screening.service;

import com.uzair.smart_resume_screening.dto.CreatePersonRequest;
import com.uzair.smart_resume_screening.dto.PersonResponse;
import com.uzair.smart_resume_screening.dto.UpdatePersonRequest;
import com.uzair.smart_resume_screening.exception.EmailAlreadyInUseException;
import com.uzair.smart_resume_screening.exception.PersonNotFoundException;
import com.uzair.smart_resume_screening.mapper.PersonMapper;
import com.uzair.smart_resume_screening.model.Person;
import com.uzair.smart_resume_screening.repo.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepo repo;
    private final PersonMapper mapper;
    public List<PersonResponse> getAllPersons(){
        return mapper.toDto(repo.findAll());
    }
    public PersonResponse getPerson(int id){
        return mapper.toDto(repo.findById(id).orElseThrow(()-> new PersonNotFoundException(id)));
    }

    public PersonResponse createPerson(CreatePersonRequest dto) {
        checkExistingEmail(dto.email());
        Person person = mapper.toEntity(dto);
        return mapper.toDto(repo.save(person));
    }

    public PersonResponse updatePerson(int id, UpdatePersonRequest dto) {
        Person person = repo.findById(id).orElseThrow(()-> new PersonNotFoundException(id));
        mapper.update(person,dto);
        return mapper.toDto(repo.save(person));
    }

    public ResponseEntity<String> deletePerson(int id) {
        repo.deleteById(id);
        return ResponseEntity.ok().body("Deleted");
    }

    private void checkExistingEmail(String email) {
        Optional<Person> existingPerson = repo.findByEmail(email);
        if(existingPerson.isPresent()){
            throw new EmailAlreadyInUseException(email);
        }
    }
}

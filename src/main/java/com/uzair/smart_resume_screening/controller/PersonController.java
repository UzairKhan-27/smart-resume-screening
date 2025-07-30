package com.uzair.smart_resume_screening.controller;

import com.uzair.smart_resume_screening.dto.CreatePersonRequest;
import com.uzair.smart_resume_screening.dto.PersonResponse;
import com.uzair.smart_resume_screening.dto.UpdatePersonRequest;
import com.uzair.smart_resume_screening.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService service;

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersons(){
        return ResponseEntity.ok(service.getAllPersons());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPerson(@PathVariable int id){
        return ResponseEntity.ok(service.getPerson(id));
    }
    @PostMapping
    public ResponseEntity<PersonResponse> createStudent(@Valid @RequestBody CreatePersonRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPerson(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updateStudent(@PathVariable int id, @Valid @RequestBody UpdatePersonRequest dto){
        return ResponseEntity.ok(service.updatePerson(id,dto));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id){
        return service.deletePerson(id);
    }

}

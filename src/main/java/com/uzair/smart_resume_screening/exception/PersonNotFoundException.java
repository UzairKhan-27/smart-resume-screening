package com.uzair.smart_resume_screening.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(int id) {
        super("Person with id "+id+" not found");
    }
}

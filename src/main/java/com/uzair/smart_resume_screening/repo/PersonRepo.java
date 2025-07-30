package com.uzair.smart_resume_screening.repo;

import com.uzair.smart_resume_screening.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person,Integer> {
    Optional<Person> findByEmail(String email);
}

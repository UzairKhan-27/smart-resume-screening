package com.uzair.smart_resume_screening.repo;

import com.uzair.smart_resume_screening.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person,Integer> {
}

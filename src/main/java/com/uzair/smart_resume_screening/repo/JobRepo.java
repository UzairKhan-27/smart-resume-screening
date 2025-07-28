package com.uzair.smart_resume_screening.repo;

import com.uzair.smart_resume_screening.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends JpaRepository<Job,Integer> {
}

package com.uzair.smart_resume_screening.repo;

import com.uzair.smart_resume_screening.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepo extends JpaRepository<Resume,Integer> {
}

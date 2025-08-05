package com.uzair.smart_resume_screening.repo;

import com.uzair.smart_resume_screening.model.JobPerson;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPersonRepo extends JpaRepository<JobPerson,Integer> {
    Optional<JobPerson> findByJobIdAndPersonId(int jobId, int personId);

    List<JobPerson> findByJobId(int jobId);

    @Transactional
    void deleteByJobIdAndPersonId(int jobId, int personId);

    @Transactional
    void deleteAllByJobId(int jobId);

    List<JobPerson> findByPersonId(int personId);

    @Transactional
    void deleteAllByPersonId(int personId);

    Page<JobPerson> findByJobId(int jobId, Pageable pageable);
}

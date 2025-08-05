package com.uzair.smart_resume_screening.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "job")
    private Set<JobPerson> jobPersons;
}

package com.uzair.smart_resume_screening.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class JobPerson implements Serializable {

    @EmbeddedId
    private JobPersonPK id;

    @ManyToOne
    @MapsId("person_id")
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @MapsId("job_id")
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @OneToOne
    @JoinColumn(name = "response_id")
    private Response response;

}

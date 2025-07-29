package com.uzair.smart_resume_screening.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class JobPersonPK implements Serializable {
    private int job_id;
    private int person_id;
}

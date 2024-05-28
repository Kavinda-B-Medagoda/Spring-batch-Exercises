package com.rd.rd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "job_application")
public class JobApplication {
    @Id
    private Long id;
    private Long candidateId;
    private String jobTitle;
}

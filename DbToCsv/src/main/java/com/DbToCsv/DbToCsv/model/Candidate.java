package com.DbToCsv.DbToCsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Candidate {
    Integer candidateid;
    Integer experience;
    String cv;
    String description;
    String firstname;
    String lastname;
    String nic;
    String qualifications;

    public Candidate() {

    }
}

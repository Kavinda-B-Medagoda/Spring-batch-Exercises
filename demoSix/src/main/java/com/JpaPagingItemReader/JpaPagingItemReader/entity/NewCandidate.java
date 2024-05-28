package com.JpaPagingItemReader.JpaPagingItemReader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "Newcandidate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidateid")
    private int candidateID;

    @Column(name = "nic", nullable = false)
    private String nic;

    @Column(name = "firstname", nullable = false)
    private String firstname;


}
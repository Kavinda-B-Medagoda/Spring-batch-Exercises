package com.rd.rd.entity;

import com.rd.rd.dto.CandidateJobApplication;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "candidate")
@SqlResultSetMapping(
        name = "CandidateJobApplicationMapping",
        classes = @ConstructorResult(
                targetClass = CandidateJobApplication.class,
                columns = {
                        @ColumnResult(name = "candidate_id", type = Long.class),
                        @ColumnResult(name = "candidate_name", type = String.class),
                        @ColumnResult(name = "job_title", type = String.class)
                }
        )
)
public class Candidate {
    @Id
    private Long id;
    private String name;
}

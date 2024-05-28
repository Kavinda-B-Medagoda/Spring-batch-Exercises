package com.rd.rd.entity;

import com.rd.rd.dto.CandidateJobApplication;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class CustomJpaNativeQueryProvider extends JpaNativeQueryProvider<CandidateJobApplication> {
    private EntityManagerFactory entityManagerFactory;

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Query createQuery() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory is not set.");
        }

        try {
            return entityManagerFactory.createEntityManager()
                    .createNativeQuery(
                            "SELECT c.id AS candidate_id, c.name AS candidate_name, ja.job_title " +
                                    "FROM candidate c " +
                                    "JOIN job_application ja ON c.id = ja.candidate_id",
                            "CandidateJobApplicationMapping"
                    );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create native query.", e);
        }
    }
}

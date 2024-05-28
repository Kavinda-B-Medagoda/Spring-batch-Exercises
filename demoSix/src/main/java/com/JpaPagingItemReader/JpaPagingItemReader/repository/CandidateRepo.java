package com.JpaPagingItemReader.JpaPagingItemReader.repository;

import com.JpaPagingItemReader.JpaPagingItemReader.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepo extends JpaRepository<Candidate,Integer> {
}

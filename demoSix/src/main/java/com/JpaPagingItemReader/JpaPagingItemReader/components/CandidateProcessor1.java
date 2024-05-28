package com.JpaPagingItemReader.JpaPagingItemReader.components;
import com.JpaPagingItemReader.JpaPagingItemReader.entity.Candidate;
import org.springframework.batch.item.ItemProcessor;

public class CandidateProcessor1 implements ItemProcessor<Candidate, Candidate> {
    @Override
    public Candidate process(Candidate candidate) throws Exception{
        System.out.println("helloooo");
        candidate.setFirstname(candidate.getFirstname()+ "PPP");
        return candidate;
    }
}

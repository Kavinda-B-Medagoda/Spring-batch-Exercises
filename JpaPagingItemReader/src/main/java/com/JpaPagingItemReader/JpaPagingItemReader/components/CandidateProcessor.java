package com.JpaPagingItemReader.JpaPagingItemReader.components;
import com.JpaPagingItemReader.JpaPagingItemReader.entity.Candidate;
import org.springframework.batch.item.ItemProcessor;

public class CandidateProcessor implements ItemProcessor<Candidate, Candidate> {
    @Override
    public Candidate process(Candidate candidate) throws Exception{
        System.out.println("ssssssssssssssssssssssssssss");
        candidate.setFirstname(candidate.getFirstname()+ "PPP");
        return candidate;
    }
}

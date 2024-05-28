package com.JpaPagingItemReader.JpaPagingItemReader.components;

import com.JpaPagingItemReader.JpaPagingItemReader.entity.Candidate;
import com.JpaPagingItemReader.JpaPagingItemReader.entity.NewCandidate;
import org.springframework.batch.item.ItemProcessor;

public class CandidateProcessor2 implements ItemProcessor<Candidate, NewCandidate> {
    @Override
    public NewCandidate process(Candidate candidate) throws Exception{

        NewCandidate newCandidate = new NewCandidate();
        newCandidate.setCandidateID(candidate.getCandidateID());
        newCandidate.setFirstname(candidate.getFirstname()+"qqq");
        newCandidate.setNic(candidate.getNic());

        return newCandidate;
    }
}

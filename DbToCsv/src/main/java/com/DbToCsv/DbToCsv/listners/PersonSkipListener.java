package com.DbToCsv.DbToCsv.listners;

import com.DbToCsv.DbToCsv.model.Candidate;
import org.springframework.batch.core.SkipListener;

public class PersonSkipListener implements SkipListener<Candidate, Candidate> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("Skipped during reading: " + t.getMessage());
        // Add any specific logic for skipped items during reading
        skipCount++;
    }

    @Override
    public void onSkipInWrite(Candidate item, Throwable t) {
        System.out.println("Skipped during writing: " + item + ", error: " + t.getMessage());
        // Add any specific logic for skipped items during writing
        skipCount++;
    }

    @Override
    public void onSkipInProcess(Candidate item, Throwable t) {
        System.out.println("Skipped during processing: " + item + ", error: " + t.getMessage());
        // Add any specific logic for skipped items during processing
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
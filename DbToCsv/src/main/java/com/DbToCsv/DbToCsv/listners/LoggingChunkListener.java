package com.DbToCsv.DbToCsv.listners;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

import java.util.List;

public class LoggingChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        System.out.println("Before chunk processing...");
        // Add any pre-processing logic here
    }

    @Override
    public void afterChunk(ChunkContext context) {
        System.out.println("After chunk processing...");
        // Add any post-processing logic here
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        System.out.println("Error during chunk processing...");
        // Add error handling or cleanup logic here
    }
}
package com.JpaPagingItemReader.JpaPagingItemReader.configuration;

import com.JpaPagingItemReader.JpaPagingItemReader.components.CandidateProcessor;
import com.JpaPagingItemReader.JpaPagingItemReader.entity.Candidate;
import com.JpaPagingItemReader.JpaPagingItemReader.repository.CandidateRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public JpaPagingItemReader<Candidate> reader() {
        return new JpaPagingItemReaderBuilder<Candidate>()
                .name("CandidateReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM Candidate c")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemProcessor<Candidate, Candidate> processor() {
        return new CandidateProcessor();
    }

    @Bean
    public ItemWriter<Candidate> writer(CandidateRepo repository) {
        return items -> {
            for (Candidate item : items) {
                repository.save(item);
            }
        };
    }

    @Bean
    public Step step1(ItemReader<Candidate> reader, ItemProcessor<Candidate, Candidate> processor, ItemWriter<Candidate> writer) {
        return stepBuilderFactory.get("step1")
                .<Candidate, Candidate>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .start(step1)
                .build();
    }
}

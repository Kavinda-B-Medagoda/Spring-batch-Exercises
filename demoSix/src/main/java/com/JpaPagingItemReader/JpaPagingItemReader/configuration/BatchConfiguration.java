package com.JpaPagingItemReader.JpaPagingItemReader.configuration;
import com.JpaPagingItemReader.JpaPagingItemReader.components.CandidateProcessor1;
import com.JpaPagingItemReader.JpaPagingItemReader.components.CandidateProcessor2;
import com.JpaPagingItemReader.JpaPagingItemReader.entity.Candidate;
import com.JpaPagingItemReader.JpaPagingItemReader.entity.NewCandidate;
import com.JpaPagingItemReader.JpaPagingItemReader.repository.CandidateRepo;
import com.JpaPagingItemReader.JpaPagingItemReader.repository.NewCandidateRepo;
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
        return new CandidateProcessor1();
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
    public JpaPagingItemReader<Candidate> reader2() {
        return new JpaPagingItemReaderBuilder<Candidate>()
                .name("CandidateReader2")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM Candidate c")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemProcessor<Candidate, NewCandidate> processor2() {
        return new CandidateProcessor2();
    }

    @Bean
    public ItemWriter<NewCandidate> writer2(NewCandidateRepo newCandidateRepo) {
        return items -> {
            for (NewCandidate item : items) {
                newCandidateRepo.save(item);
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
    public Step step2(ItemReader<Candidate> reader2, ItemProcessor<Candidate, NewCandidate> processor2, ItemWriter<NewCandidate> writer2) {
        return stepBuilderFactory.get("step2")
                .<Candidate, NewCandidate>chunk(10)
                .reader(reader2)
                .processor(processor2)
                .writer(writer2)
                .build();
    }

    @Bean
    public Job importUserJob(Step step1, Step step2) {
        return jobBuilderFactory.get("importUserJob")
                .start(step1)
                .next(step2)
                .build();
    }
}

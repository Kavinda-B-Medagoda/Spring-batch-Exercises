package com.rd.rd.configurations;

import com.rd.rd.repository.CandidateRepo;
import com.rd.rd.dto.CandidateJobApplication;
import com.rd.rd.entity.CustomJpaNativeQueryProvider;
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
public class Config {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<CandidateJobApplication> reader() throws Exception {
        CustomJpaNativeQueryProvider queryProvider = new CustomJpaNativeQueryProvider();
        queryProvider.setEntityManagerFactory(entityManagerFactory);
        queryProvider.afterPropertiesSet();

        return new JpaPagingItemReaderBuilder<CandidateJobApplication>()
                .name("CandidateJobApplicationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryProvider(queryProvider)
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemProcessor<CandidateJobApplication, CandidateJobApplication> processor() {
        return item -> {
            // process the item
            return item;
        };
    }

    @Bean
    public ItemWriter<CandidateJobApplication> writer(CandidateRepo repository) {
        return items -> {
            for (CandidateJobApplication item : items) {
                // Write the item
                System.out.println(item);
            }
        };
    }

    @Bean
    public Step step1(ItemReader<CandidateJobApplication> reader, ItemProcessor<CandidateJobApplication, CandidateJobApplication> processor, ItemWriter<CandidateJobApplication> writer) {
        return stepBuilderFactory.get("step1")
                .<CandidateJobApplication, CandidateJobApplication>chunk(10)
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

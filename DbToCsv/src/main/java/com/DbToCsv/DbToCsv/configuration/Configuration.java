package com.DbToCsv.DbToCsv.configuration;

import com.DbToCsv.DbToCsv.listners.*;
import com.DbToCsv.DbToCsv.model.Candidate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@org.springframework.context.annotation.Configuration
@EnableBatchProcessing
public class Configuration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Candidate> reader(){
        JdbcCursorItemReader<Candidate> reader = new JdbcCursorItemReader<Candidate>();
        reader.setDataSource(dataSource);
        reader.setSql("select candidateid, experience, firstname, lastname from candidate");
        reader.setRowMapper(new RowMapper<Candidate>() {
            @Override
            public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
                Candidate candidate = new Candidate();
                candidate.setCandidateid(rs.getInt("candidateid"));
                candidate.setFirstname(rs.getString("firstname"));
                candidate.setLastname(rs.getString("lastname"));
                candidate.setExperience(rs.getInt("experience"));
                return candidate;
            }
        });
        return reader;
    }

    @Bean
    public FlatFileItemWriter<Candidate> writer(){
        FlatFileItemWriter<Candidate> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("/home/kmedagoda/Documents/CSV/output.csv"));
        DelimitedLineAggregator<Candidate> aggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<Candidate> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"candidateid", "firstname", "lastname", "experience"});
        aggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(aggregator);
        return writer;
    }

    @Bean
    public Step executeStep(){
        return stepBuilderFactory.get("executeStep").<Candidate, Candidate>chunk(10).reader(reader()).writer(writer()).listener(new LoggingStepExecutionListener()).listener(new LoggingChunkListener()).listener(new PersonItemReadListener()) .listener(new PersonSkipListener()).build();
    }

    @Bean
    public Job processJob(){
        return jobBuilderFactory.get("processJob").incrementer(new RunIdIncrementer()).listener(new LoggingJobExecutionListener()).flow(executeStep()).end().build();
    }
}

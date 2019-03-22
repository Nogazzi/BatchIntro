package com.example.BatchIntro.configuration;

import com.example.BatchIntro.model.InputRunningEvent;
import com.example.BatchIntro.model.OutputRatedRunningEvent;
import com.example.BatchIntro.processing.EventItemCalculatePrizeProcessor;
import com.example.BatchIntro.processing.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    @Autowired
    private JobCompletionNotificationListener jobCompletionNotificationListener;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<InputRunningEvent> reader() {
        return new FlatFileItemReaderBuilder<InputRunningEvent>()
                .name("RunningEventReader")                                             // nazwa readera
                .resource(new ClassPathResource("events-input.csv"))                    // źródło danych
                .delimited()
                .names(new String[]{"name", "distance", "prize"})                       // matchowanie wartości na nazwy pól
                .fieldSetMapper(new BeanWrapperFieldSetMapper<InputRunningEvent>() {{   // mapper ze wskazaniem na klasę do zmatchowania
                    setTargetType(InputRunningEvent.class);}})
                .build();
    }

    @Bean
    public EventItemCalculatePrizeProcessor processor() {
        return new EventItemCalculatePrizeProcessor();
    }

    @Bean
    public FlatFileItemWriter<OutputRatedRunningEvent> writer() {
        DelimitedLineAggregator<OutputRatedRunningEvent> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        return new FlatFileItemWriterBuilder<OutputRatedRunningEvent>()
                .name("RunningEventWriter")
                .resource(new ClassPathResource("events-output.csv"))
                .lineAggregator(lineAggregator)
                .build();
    }

    @Bean
    public Job importEventJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importEventJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(FlatFileItemWriter<OutputRatedRunningEvent> writer) {
        return stepBuilderFactory.get("step1")
                .<InputRunningEvent, OutputRatedRunningEvent> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }



}

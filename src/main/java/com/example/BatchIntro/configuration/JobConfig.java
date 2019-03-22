package com.example.BatchIntro.configuration;

import com.example.BatchIntro.model.BaseRunningEvent;
import com.example.BatchIntro.model.PrizedRunningEvent;
import com.example.BatchIntro.model.RatedRunningEvent;
import com.example.BatchIntro.processing.EventItemCalculatePrizeProcessor;
import com.example.BatchIntro.processing.EventItemRatingProcessor;
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
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
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
    public FlatFileItemReader<BaseRunningEvent> baseEventReader() {
        return new FlatFileItemReaderBuilder<BaseRunningEvent>()
                .name("BaseRunningEventReader")                                             // nazwa readera
                .resource(new ClassPathResource("base-events.csv"))                    // źródło danych
                .delimited()
                .names(new String[]{"name", "distance", "prize"})                       // matchowanie wartości na nazwy pól
                .fieldSetMapper(new BeanWrapperFieldSetMapper<BaseRunningEvent>() {{   // mapper ze wskazaniem na klasę do zmatchowania
                    setTargetType(BaseRunningEvent.class);}})
                .build();
    }

    @Bean
    public FlatFileItemReader<PrizedRunningEvent> prizedEventReader() {
        return new FlatFileItemReaderBuilder<PrizedRunningEvent>()
                .name("PrizedRunningEventReader")                                       // nazwa readera
                .resource(new ClassPathResource("prized-events.csv"))                   // źródło danych
                .delimited()
                .names(new String[]{"name", "distance", "prize", "prizePerKm"})       // matchowanie wartości na nazwy pól
                .fieldSetMapper(new BeanWrapperFieldSetMapper<PrizedRunningEvent>() {{    // mapper ze wskazaniem na klasę do zmatchowania
                    setTargetType(PrizedRunningEvent.class);}})
                .build();
    }

    @Bean
    public EventItemCalculatePrizeProcessor prizeCalculateProcessor() {
        return new EventItemCalculatePrizeProcessor();
    }

    @Bean
    public EventItemRatingProcessor ratingProcessor() {
        return new EventItemRatingProcessor();
    }

    @Bean
    public FlatFileItemWriter<PrizedRunningEvent> prizedEventWriter() {
        return new FlatFileItemWriterBuilder<PrizedRunningEvent>()
                .name("PrizedRunningEventWriter")
                .resource(new ClassPathResource("prized-events.csv"))
                .lineAggregator(new DelimitedLineAggregator<PrizedRunningEvent>() {
                    {
                        setDelimiter(",");
                        setFieldExtractor(new BeanWrapperFieldExtractor<PrizedRunningEvent>(){
                            {
                                setNames(new String[]{"name", "distance", "prize", "prizePerKm"});
                            }
                        });
                    }
                })
                .build();
    }

    @Bean
    public FlatFileItemWriter<RatedRunningEvent> ratedEventWriter() {
        return new FlatFileItemWriterBuilder<RatedRunningEvent>()
                .name("RatedRunningEventWriter")
                .resource(new ClassPathResource("rated-events.csv"))
                .lineAggregator(new DelimitedLineAggregator<RatedRunningEvent>() {
                    {
                        setDelimiter(",");
                        setFieldExtractor(new BeanWrapperFieldExtractor<RatedRunningEvent>(){
                            {
                                setNames(new String[]{"name", "distance", "prize", "prizePerKm", "rate"});
                            }
                        });
                    }
                })
                .build();
    }

    @Bean
    public Job RunningEventJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("RunningEventJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(calculatePrizeStep())
                .next(rateEventStep())
                .build();
    }

    @Bean
    public Step calculatePrizeStep() {
        return stepBuilderFactory.get("EventReaderStep")
                .<BaseRunningEvent, PrizedRunningEvent> chunk(10)
                .reader(baseEventReader())
                .processor(prizeCalculateProcessor())
                .writer(prizedEventWriter())
                .build();
    }

    @Bean
    public Step rateEventStep() {
        return stepBuilderFactory.get("RateEventStep")
                .<PrizedRunningEvent, RatedRunningEvent> chunk(10)
                .reader(prizedEventReader())
                .processor(ratingProcessor())
                .writer(ratedEventWriter())
                .build();
    }
}

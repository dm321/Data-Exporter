package com.dw.data.exporter.common;

import com.dw.data.exporter.model.FileHolder;
import com.dw.data.exporter.reader.CustomFileReader;
import com.dw.data.exporter.writer.LandClassifier;
import com.dw.data.exporter.writer.LoggingWriter;
import com.dw.data.exporter.model.Kunde;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;


@Import({LandClassifier.class, CustomFileReader.class, LoggingWriter.class})
public class JobConfiguration {


    @Bean
    public Job myJob(JobRepository jobRepository, @Qualifier("exportDataToCsvStep") Step exportDataToCsv, @Qualifier("readAndExportCsv") Step exportDataToCsvStep) {
        return new JobBuilder("myJob", jobRepository).incrementer(new RunIdIncrementer())
                .start(exportDataToCsv)
                .next(exportDataToCsvStep)
                .build();
    }

    @Bean
    public Step exportDataToCsvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemStreamReader<Kunde> kundeReader, @Qualifier("landClassifier") Classifier classifier) {
        return new StepBuilder("exportDataToCsv", jobRepository).<Kunde, Kunde>chunk(100, transactionManager)
                .reader(kundeReader)
                .writer(classifierCompositeItemWriter(classifier))
                .build();
    }

    @Bean
    public Step readAndExportCsv(JobRepository jobRepository, PlatformTransactionManager transactionManager, CustomFileReader customFileReader, LoggingWriter loggingWriter) {
        return new StepBuilder("readAndExportCsv", jobRepository).<FileHolder,FileHolder>chunk(10,
                        transactionManager)
                .reader(customFileReader)
                .writer(loggingWriter)
                .build();
    }

    @Bean(name = "kundeReader")
    public ItemStreamReader<Kunde> kundeReader(EntityManagerFactory entityManagerFactory) {
        JpaPagingItemReader reader = new JpaPagingItemReader();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("from Kunde order by land, id desc");
        reader.setPageSize(100);
        return reader;
    }

    @Bean
    public ClassifierCompositeItemWriter classifierCompositeItemWriter(Classifier classifier) {
        ClassifierCompositeItemWriter classifierCompositeItemWriter = new ClassifierCompositeItemWriter();
        classifierCompositeItemWriter.setClassifier(classifier);
        return classifierCompositeItemWriter;
    }

}

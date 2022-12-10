package com.dw.data.exporter;

import com.dw.data.exporter.common.JobConfiguration;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableBatchProcessing
@Import({JobConfiguration.class, BatchAutoConfiguration.class})
@ComponentScan
public class FileExporterApplication implements CommandLineRunner {

    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;

    public static void main(String[] args) {
        SpringApplication.run(FileExporterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addString("startTime", LocalDateTime.now()
                        .toString())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);

    }
}

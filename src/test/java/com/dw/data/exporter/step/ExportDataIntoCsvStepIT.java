package com.dw.data.exporter.step;

import com.dw.data.exporter.common.AbstractIntegrationTest;
import com.dw.data.exporter.common.CsvProperties;
import com.dw.data.exporter.common.JobConfiguration;
import com.dw.data.exporter.writer.LandClassifier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.nio.file.Path;

@SpringBatchTest
@Import({JobConfiguration.class, LandClassifier.class, BatchAutoConfiguration.class})
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExportDataIntoCsvStepIT extends AbstractIntegrationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private CsvProperties csvProperties;

    @TempDir
    Path directory;

    @BeforeEach
    void setUp() {
        String dirPath = directory.toString();
        csvProperties.setOutputPath(dirPath);

    }

    @Test
    public void givenChunksJob_whenJobEnds_thenStatusCompleted() throws Exception {

        Job job = jobLauncherTestUtils.getJob();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assertions.assertThat(jobExecution.getExitStatus())
                .isEqualTo(ExitStatus.COMPLETED);
    }
}

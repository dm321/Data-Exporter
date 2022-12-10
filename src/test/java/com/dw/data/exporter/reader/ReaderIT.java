package com.dw.data.exporter.reader;

import com.dw.data.exporter.common.AbstractIntegrationTest;
import com.dw.data.exporter.common.JobConfiguration;
import com.dw.data.exporter.model.Kunde;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@Import(JobConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBatchTest
public class ReaderIT extends AbstractIntegrationTest {

    @Autowired
    @Qualifier("kundeReader")
    private ItemStreamReader<Kunde> kundeReader;

    private StepExecution stepExecution;

    @Test
    void kundenReaderTest() throws Exception {

        kundeReader.open(getStepExecution().getExecutionContext());
        Kunde readKunde = kundeReader.read();

        assertThat(readKunde).isNotNull();
        assertThat(readKunde.getAuftaege()).isNotEmpty();
    }

    public StepExecution getStepExecution() {
        stepExecution = MetaDataInstanceFactory.createStepExecution();
        return stepExecution;
    }
}

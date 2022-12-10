package com.dw.data.exporter.writer;

import com.dw.data.exporter.common.CsvProperties;
import com.dw.data.exporter.model.Auftrag;
import com.dw.data.exporter.model.Kunde;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBatchTest
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@Import(LandClassifier.class)
@EnableConfigurationProperties(value = CsvProperties.class)
public class LandClassifierTest {


    @TempDir
    Path directory;
    @Autowired
    private Classifier<Kunde, ItemStreamWriter<? super Kunde>> classifier;
    private StepExecution stepExecution;
    @Autowired
    private CsvProperties csvProperties;

    @BeforeEach
    void setUp() {
        String dirPath = directory.toString();
        csvProperties.setOutputPath(dirPath);

    }

    public StepExecution getStepExecution() {
        stepExecution = MetaDataInstanceFactory.createStepExecution();
        return stepExecution;
    }

    @Test
    void landClassifierTest_Positive() throws Exception {
        long amountOfCreatedCsvFilesBefore = Files.list(directory.toAbsolutePath())
                .count();
        Kunde[] kundenLArray = getKundenLArray();
        ItemStreamWriter<? super Kunde> writer = classifier.classify(kundenLArray[0]);
        writer.open(getStepExecution().getExecutionContext());
        writer.write(Chunk.of(kundenLArray[0]));
        writer.close();

        long amountOfCreatedCsvFilesAfter = Files.list(directory.toAbsolutePath())
                .count();

        Assertions.assertThat(amountOfCreatedCsvFilesAfter)
                .isGreaterThan(amountOfCreatedCsvFilesBefore);
    }

    private Kunde[] getKundenLArray() {
        List<Kunde> kunden = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            List<Auftrag> auftrage = List.of(Auftrag.builder()
                    .artikelnummer("TestArtikelnummer" + i)
                    .auftragId("TestAuftragId" + i)
                    .created("TestCreated")
                    .lastChange("TestLastChange")
                    .build());

            Kunde kunde = Kunde.builder()
                    .firmenname("TestFirmenname" + i)
                    .land("TestLand" + i)
                    .nachname("TestNachname" + i)
                    .vorname("TestVorname" + i)
                    .ort("TestOrt" + i)
                    .plz("TestPlz" + i)
                    .strasse("TestStrassse" + i)
                    .strassenzusatz("TestStrassenzusatz" + i)
                    .auftaege(auftrage)
                    .kundenId(Long.valueOf(i))
                    .build();
            kunden.add(kunde);

        }
        return kunden.toArray(new Kunde[20]);
    }


}

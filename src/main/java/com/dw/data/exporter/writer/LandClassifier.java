package com.dw.data.exporter.writer;

import com.dw.data.exporter.common.CsvProperties;
import com.dw.data.exporter.model.Kunde;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class LandClassifier implements Classifier<Kunde, ItemStreamWriter<? super Kunde>> {

    private Map<String, CsvWriter> csvWriterPerLand = new HashMap<>();
    @Autowired
    private CsvProperties csvProperties;

    @Override
    public ItemStreamWriter<? super Kunde> classify(Kunde kunde) {
        String land = kunde.getLand();
        if (isWriterForLandAvaliable(land)) {
            return csvWriterPerLand.get(land);
        }
        CsvWriter csvWriter = new CsvWriter(computeOutputPath(land), csvProperties);
        csvWriter.open(new ExecutionContext());
        csvWriterPerLand.put(land, csvWriter);
        return csvWriterPerLand.get(land);
    }

    private boolean isWriterForLandAvaliable(String land) {
        return csvWriterPerLand.containsKey(land);
    }

    private String computeOutputPath(String land) {
        String outputPath = this.csvProperties.getOutputPath();
        String fullOutputPath = String.format("%s%s%s%s.csv", outputPath, File.separator, land, LocalDate.now());
        return fullOutputPath;
    }
}

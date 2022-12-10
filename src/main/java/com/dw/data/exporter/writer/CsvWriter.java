package com.dw.data.exporter.writer;

import com.dw.data.exporter.common.CsvProperties;
import com.dw.data.exporter.model.Kunde;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

public class CsvWriter extends FlatFileItemWriter<Kunde> {

    private static final String DELIMITER = ",";

    public CsvWriter(String outputPath, CsvProperties csvProperties) {
        Resource outputResource = new FileSystemResource(outputPath);
        this.setResource((WritableResource) outputResource);
        this.setAppendAllowed(true);

        CustomHeaderAppender headerWriter = new CustomHeaderAppender(csvProperties.getHeaders(), DELIMITER);
        this.setHeaderCallback(headerWriter);
        this.setLineAggregator(getDelimitedLineAggregator(csvProperties));
    }

    private DelimitedLineAggregator<Kunde> getDelimitedLineAggregator(CsvProperties csvProperties) {
        BeanWrapperFieldExtractor fieldValueExtractor = new BeanWrapperFieldExtractor();
        fieldValueExtractor.setNames(csvProperties.getFieldNames()
                .toArray(new String[csvProperties.getFieldNames()
                        .size()]));

        DelimitedLineAggregator<Kunde> kundeDelimitedLineAggregator = new DelimitedLineAggregator<>();
        kundeDelimitedLineAggregator.setDelimiter(DELIMITER);
        kundeDelimitedLineAggregator.setFieldExtractor(fieldValueExtractor);
        return kundeDelimitedLineAggregator;
    }
}

//package com.dw.file.exporter.common;
//
//import com.dw.file.exporter.model.Kunde;
//import com.dw.file.exporter.writer.CustomHeaderAppender;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
//import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.WritableResource;
//
//public class WriterConfiguration {
//
//    private static final String DELIMITER = ",";
//
//    private Resource outputResource = new FileSystemResource("D:/Development/outputData.csv");
//
////    @Bean
////    @StepScope
//    public ItemWriter<Kunde> writer(CsvProperties csvProperties, DelimitedLineAggregator<Kunde> delimitedLineAggregator) {
//        //Create writer instance
//        FlatFileItemWriter<Kunde> writer = new FlatFileItemWriter<>();
//
//        //Set output file location
//        writer.setResource((WritableResource) outputResource);
//        //All job repetitions should "append" to same output file
//        writer.setAppendAllowed(true);
//        CustomHeaderAppender headerWriter = new CustomHeaderAppender(csvProperties.getHeaders(), DELIMITER);
//        writer.setHeaderCallback(headerWriter);
//        //Name field values sequence based on object properties
//        writer.setLineAggregator(delimitedLineAggregator);
//
//        return writer;
//    }
//
//    @Bean
//    public DelimitedLineAggregator<Kunde> delimitedLineAggregator(CsvProperties csvProperties) {
//        BeanWrapperFieldExtractor fieldValueExtractor = new BeanWrapperFieldExtractor();
//        fieldValueExtractor.setNames(csvProperties.getFieldNames()
//                .toArray(new String[csvProperties.getFieldNames()
//                        .size()]));
//
//        DelimitedLineAggregator<Kunde> kundeDelimitedLineAggregator = new DelimitedLineAggregator<>();
//        kundeDelimitedLineAggregator.setDelimiter(DELIMITER);
//        kundeDelimitedLineAggregator.setFieldExtractor(fieldValueExtractor);
//        return kundeDelimitedLineAggregator;
//    }
//
//
//}

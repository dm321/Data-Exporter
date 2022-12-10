package com.dw.data.exporter.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "file.exporter.csv")
@Data
@Component
@PropertySource("classpath:application.yml")
@NoArgsConstructor
public class CsvProperties {

    private List<String> headers;

    private List<String> fieldNames;

    private String outputPath;

    private String inputPath;

}

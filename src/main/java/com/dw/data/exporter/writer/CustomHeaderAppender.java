package com.dw.data.exporter.writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CustomHeaderAppender implements FlatFileHeaderCallback {

    private List<String> headers;
    private String delimiter;

    public CustomHeaderAppender(List<String> headers, String delimiter) {
        this.headers = headers;
        this.delimiter = delimiter;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {

        for (String header : headers) {
            writer.write(header + delimiter);
        }

    }
}

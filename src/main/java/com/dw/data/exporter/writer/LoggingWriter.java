package com.dw.data.exporter.writer;

import com.dw.data.exporter.model.FileHolder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class LoggingWriter implements ItemStreamWriter<FileHolder> {
//    private RestTemplate restTemplate;

    @Value("${csv.server.url}")
    private String url;

    @Autowired
    public LoggingWriter() {

//        this.restTemplate = builder.build();
    }

    int counter = 0;
    @Override
    public void write(Chunk<? extends FileHolder> chunk) throws Exception {

        chunk.getItems()
                .stream()
                .forEach(holder -> sendFile(holder));

    }

    public void sendFile(FileHolder fileHolder) {
        // TODO not implemented
    }

}

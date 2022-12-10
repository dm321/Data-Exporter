package com.dw.data.exporter.reader;

import com.dw.data.exporter.common.CsvProperties;
import com.dw.data.exporter.model.FileHolder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * K - Filename
 * V - File
 */
@Component
public class CustomFileReader implements ItemStreamReader<FileHolder> {

    @Autowired
    CsvProperties csvProperties;

    private List<Path> processedPaths = new CopyOnWriteArrayList<>();

    @Override
    public FileHolder read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Optional<Path> fileToRead = findFilesToRead();
        if (fileToRead.isPresent() && !processedPaths.contains(fileToRead.get())) {
            Path path = fileToRead.get();
            var fileName = path.getFileName()
                    .toString();
            var file = Files.readAllBytes(path);
            processedPaths.add(path);
            return new FileHolder(fileName, file);
        }
        return null;
    }

    private Optional<Path> findFilesToRead() {

        try (Stream<Path> paths = Files.walk(Paths.get(csvProperties.getInputPath()))) {
            return paths.filter(Files::isRegularFile)
                    .filter(path -> !processedPaths.contains(path))
                    .sorted()
                    .findFirst();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

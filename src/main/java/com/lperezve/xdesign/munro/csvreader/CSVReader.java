package com.lperezve.xdesign.munro.csvreader;

import com.lperezve.xdesign.munro.model.Munro;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CSVReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVReader.class);

    @Cacheable("munroFile")
    @EventListener(ApplicationReadyEvent.class)
    public List<Munro> csvReader() {
        URL res = getClass().getClassLoader().getResource("munrotab_v6.2.csv");
        List<Munro> munroList = new ArrayList<>();
        try {
            File file = Paths.get(Objects.requireNonNull(res).toURI()).toFile();
            String filePath = file.getAbsolutePath();
            LOGGER.info("+++ Retrieving and mapping data from { " + filePath + " } +++" + filePath);
            munroList = new CsvToBeanBuilder(new FileReader(filePath))
                    .withSeparator(',')
                    .withType(Munro.class)
                    .withFilter(line -> line[0].length() != 0)
                    .build()
                    .parse();
            LOGGER.info("+++ Number of rows retrieved from munro file: " + munroList.size() + " +++");
        } catch (URISyntaxException e) {
            LOGGER.error("+++ Error getting the URI file: " + e.getMessage() + " +++");
        } catch (FileNotFoundException e) {
            LOGGER.error("+++ Error finding the File: " + e.getMessage() + " +++");
        }
        return munroList;
    }
}

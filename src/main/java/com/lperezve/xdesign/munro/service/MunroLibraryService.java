package com.lperezve.xdesign.munro.service;

import com.lperezve.xdesign.munro.constants.Messages;
import com.lperezve.xdesign.munro.csvreader.CsvReader;
import com.lperezve.xdesign.munro.dto.GlobalResponseDTO;
import com.lperezve.xdesign.munro.dto.MunroResponseDTO;
import com.lperezve.xdesign.munro.model.Munro;
import com.lperezve.xdesign.munro.repository.MunroLibraryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lperezve.xdesign.munro.constants.Constants.*;

@Service
public class MunroLibraryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MunroLibraryService.class);

    @Autowired
    private MunroLibraryRepository munroLibraryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public GlobalResponseDTO saveData() throws URISyntaxException, FileNotFoundException {

        List<Munro> munroList = CsvReader.csvReader(CsvReader.getFilepath(FILENAME_CSV));
        LOGGER.info("+++ Number of rows retrieved from munro file {} +++ ", munroList.size());

        munroLibraryRepository.saveData(munroList);

        return GlobalResponseDTO.builder().description(Messages.HTTP_200_DATA_LOADED).build();
    }

    public List<MunroResponseDTO> findMunros(String category, String sort, String limit, String minHeight, String maxHeight) {
        LOGGER.info("+++ Filtering data by: ");

        List<Munro> munroList = munroLibraryRepository.findMunros();
        LOGGER.info("Munros retrieved: " + munroList.size());

        Stream<Munro> munroStream = munroList.stream();
        munroStream = filteringByCategory(munroStream, category);
        munroStream = addingMinHeight(munroStream, minHeight);
        munroStream = addingMaxHeight(munroStream, maxHeight);
        munroStream = addingLimit(munroStream, limit);

        munroStream = sortingBy(munroStream, sort);

        munroList = munroStream.collect(Collectors.toList());
        LOGGER.info("Munros to return: " + munroList.size());

        return munroList.stream().map(MunroResponseDTO::from).collect(Collectors.toList());
    }

    private Stream<Munro> filteringByCategory(Stream<Munro> munroStream, String category) {
        LOGGER.info("category: {}", category);

        munroStream = (category != null)
                ? munroStream.filter(munro -> munro.getCategoryPost1997().equals(category))
                : munroStream.filter(munro -> munro.getCategoryPost1997().equals(MUN) || munro.getCategoryPost1997().equals(TOP));
        return munroStream;
    }


    private Stream<Munro> addingLimit(Stream<Munro> munroStream, String limit) {
        LOGGER.info("limit: {}", limit);

        if (limit != null) {
            munroStream = munroStream.limit(Integer.parseInt(limit));
        }
        return munroStream;
    }

    private Stream<Munro> addingMinHeight(Stream<Munro> munroStream, String minHeight) {
        LOGGER.info("minHeight: {}", minHeight);

        if (minHeight != null) {
            munroStream = munroStream.filter(munro -> munro.getHeightM() >= Float.parseFloat(minHeight));
        }
        return munroStream;
    }

    private Stream<Munro> addingMaxHeight(Stream<Munro> munroStream, String maxHeight) {
        LOGGER.info("maxHeight:{}", maxHeight);

        if (maxHeight != null) {
            munroStream = munroStream.filter(munro -> munro.getHeightM() <= Float.parseFloat(maxHeight));
        }
        return munroStream;
    }

    private Stream<Munro> sortingBy(Stream<Munro> munroStream, String sort) {
        LOGGER.info("sortingBy: {}", sort);
        if (sort != null) {
            if (sort.contains(DESC)) {
                munroStream = descSorting(munroStream, sort);
            } else {
                munroStream = ascSorting(munroStream, sort);
            }
        }
        return munroStream;
    }

    private Stream<Munro> descSorting(Stream<Munro> munroStream, String sort) {
        if (sort.contains(HEIGHT)) {
            return munroStream.sorted(Comparator.comparing(Munro::getHeightM).reversed());
        } else {
            return munroStream.sorted(Comparator.comparing(Munro::getName).reversed());
        }
    }

    private Stream<Munro> ascSorting(Stream<Munro> munroStream, String sort) {
        if (sort.contains(HEIGHT)) {
            return munroStream.sorted(Comparator.comparing(Munro::getHeightM));
        } else {
            return munroStream.sorted(Comparator.comparing(Munro::getName));
        }
    }

}
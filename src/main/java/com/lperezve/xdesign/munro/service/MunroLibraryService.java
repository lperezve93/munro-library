package com.lperezve.xdesign.munro.service;

import com.lperezve.xdesign.munro.csvreader.CsvReader;
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
import java.util.ArrayList;
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
    public void saveData() {
        List<Munro> munroList = new ArrayList<>();

        try {
            munroList = CsvReader.csvReader(CsvReader.getFilepath(FILENAME_CSV));
            LOGGER.info("+++ Number of rows retrieved from munro file {} +++ ", munroList.size());
        } catch (URISyntaxException e) {
            LOGGER.error("+++ Error getting the URI file {} +++ \n Error message: {} ", FILENAME_CSV, e.getMessage());
        } catch (FileNotFoundException e) {
            LOGGER.error("+++ Error finding the file {} +++ \n Error message: {} ", FILENAME_CSV, e.getMessage());
        }

        munroLibraryRepository.saveData(munroList);
    }

    public List<MunroResponseDTO> findMunros(String category, String sort, Integer limit, Float minHeight, Float maxHeight) {
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


    private Stream<Munro> addingLimit(Stream<Munro> munroStream, Integer limit) {
        LOGGER.info("limit: {}", limit);

        if (limit != null) {
            munroStream = munroStream.limit(limit);
        }
        return munroStream;
    }

    private Stream<Munro> addingMinHeight(Stream<Munro> munroStream, Float minHeight) {
        LOGGER.info("minHeight: {}", minHeight);

        if (minHeight != null) {
            munroStream = munroStream.filter(munro -> munro.getHeightM() >= minHeight);
        }
        return munroStream;
    }

    private Stream<Munro> addingMaxHeight(Stream<Munro> munroStream, Float maxHeight) {
        LOGGER.info("maxHeight:{}", maxHeight);

        if (maxHeight != null) {
            munroStream = munroStream.filter(munro -> munro.getHeightM() <= maxHeight);
        }
        return munroStream;
    }

    //namedesc - name
    private Stream<Munro> sortingBy(Stream<Munro> munroStream, String sort) {
        LOGGER.info("sortingBy: {}", sort);
        if (sort != null) {
            if (!isMultipleSorting(sort)) {
                if (sort.contains(DESC)) {
                    munroStream = descSorting(munroStream, sort);
                } else {
                    munroStream = ascSorting(munroStream, sort);
                }
            }
//            else {
//                munroStream = multipleSorting(munroStream, sort);
//            }
        }
        return munroStream;
    }

    private boolean isMultipleSorting(String sort) {
        return sort.contains(PLUS) ? Boolean.TRUE : Boolean.FALSE;
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

//    private Stream<Munro> multipleSorting(Stream<Munro> munroStream, String sort){
//        String[] multipleSort = sort.split(PLUS);
//        if (multipleSort[0].contains(ASC)) return ascSorting(munroStream, multipleSort[0]);
//        else return descSorting(munroStream, multipleSort[0]);
//
//    }
}
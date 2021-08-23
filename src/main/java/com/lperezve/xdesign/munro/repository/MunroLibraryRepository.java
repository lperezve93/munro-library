package com.lperezve.xdesign.munro.repository;

import com.lperezve.xdesign.munro.model.Munro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MunroLibraryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MunroLibraryRepository.class);

    private static List<Munro> mockMunroList = new ArrayList<>();

    public List<Munro> saveData(List<Munro> munroList) {
        LOGGER.info("+++ Repository - saving data +++");
        mockMunroList = munroList;
        return mockMunroList;
    }

    public List<Munro> findMunros() {
        LOGGER.info("+++ Repository - finding Munros +++");
        return mockMunroList;
    }

}
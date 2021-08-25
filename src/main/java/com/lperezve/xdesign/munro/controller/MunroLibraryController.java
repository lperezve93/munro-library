package com.lperezve.xdesign.munro.controller;

import com.lperezve.xdesign.munro.MunroLibraryApplication;
import com.lperezve.xdesign.munro.dto.MunroResponseDTO;
import com.lperezve.xdesign.munro.exception.MunroLimitException;
import com.lperezve.xdesign.munro.service.MunroLibraryService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.AttributeNotFoundException;
import java.util.List;
import java.util.regex.Pattern;

import static com.lperezve.xdesign.munro.constants.Constants.MUN;
import static com.lperezve.xdesign.munro.constants.Constants.TOP;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Api(value = "API to retrieve a list of Munros and Munro Tops from Munro's Library")
@RequestMapping("/munro-library")
public class MunroLibraryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MunroLibraryApplication.class);

    @Autowired
    private MunroLibraryService munroLibraryService;

    @Operation(summary = "Get a list of Munros by applying filters")
    @GetMapping(path = "/filtering", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MunroResponseDTO>> findMunros(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "minHeight", required = false) String minHeight,
            @RequestParam(value = "maxHeight", required = false) String maxHeight) throws AttributeNotFoundException, MunroLimitException {

        validateFilters(category, sort, limit, minHeight, maxHeight);

        List<MunroResponseDTO> munroResponseDTOList = munroLibraryService.findMunros(category, sort, limit, minHeight, maxHeight);
        return new ResponseEntity<>(munroResponseDTOList, HttpStatus.OK);
    }

    private void validateFilters(String category, String sort, String limit, String minHeight, String maxHeight) throws AttributeNotFoundException, MunroLimitException {
        validateCategory(category);
        validateLimit(limit);
    }

    private void validateCategory(String category) throws AttributeNotFoundException {
        if (category != null && (!category.equals(MUN) && !category.equals(TOP)))
            throw new AttributeNotFoundException();
    }

    private void validateLimit(String limit) throws MunroLimitException {
        Pattern pattern = Pattern.compile("[0-9]+");
        if (limit != null && !pattern.matcher(limit).matches())
            throw new MunroLimitException();
    }


}
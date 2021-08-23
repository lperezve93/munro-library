package com.lperezve.xdesign.munro.controller;

import com.lperezve.xdesign.munro.MunroLibraryApplication;
import com.lperezve.xdesign.munro.dto.MunroResponseDTO;
import com.lperezve.xdesign.munro.service.MunroLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Api(value="API to retrieve a list of Munros and Munro Tops from Munro's Library")
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
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "minHeight", required = false) Float minHeight,
            @RequestParam(value = "maxHeight", required = false) Float maxHeight) {

        List<MunroResponseDTO> munroResponseDTOList = munroLibraryService.findMunros(category, sort, limit, minHeight, maxHeight);
        return new ResponseEntity<>(munroResponseDTOList, HttpStatus.OK);
    }

}
package com.lperezve.xdesign.munro.exception;

import com.lperezve.xdesign.munro.dto.GlobalResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import static com.lperezve.xdesign.munro.constants.Messages.*;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    @ExceptionHandler(URISyntaxException.class)
    public final ResponseEntity<GlobalResponseDTO> handleNotFoundException(URISyntaxException exception) {
        LOGGER.error("URISyntaxException, Message: {}", exception.getMessage());
        return new ResponseEntity<>(GlobalResponseDTO.builder().description(ERROR_NO_FILENAME_FOUND).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<GlobalResponseDTO> handleNotFoundException(FileNotFoundException exception) {
        LOGGER.error("FileNotFoundException, Message: {}", exception.getMessage());
        return new ResponseEntity<>(GlobalResponseDTO.builder().description(ERROR_NO_DATA_FOUND).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MunroCategoryException.class)
    public final ResponseEntity<GlobalResponseDTO> handleNotFoundException(MunroCategoryException exception) {
        LOGGER.error("MunroCategoryException, Message: {}", exception.getMessage());
        return new ResponseEntity<>(GlobalResponseDTO.builder().description(INVALID_CATEGORY).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MunroLimitException.class)
    public final ResponseEntity<GlobalResponseDTO> handleNotFoundException(MunroLimitException exception) {
        LOGGER.error("MunroLimitException, Message: {}", exception.getMessage());
        return new ResponseEntity<>(GlobalResponseDTO.builder().description(INVALID_LIMIT).build(), HttpStatus.BAD_REQUEST);
    }

}

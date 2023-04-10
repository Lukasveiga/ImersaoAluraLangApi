package com.br.imersaojava.langsapi.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(LangNotFoundException.class)
    public ResponseEntity<?> langNotFound(LangNotFoundException e, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LangAlreadyExistsException.class)
    public ResponseEntity<?> langAlreadyExists(LangAlreadyExistsException e, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.OK);
    }
}

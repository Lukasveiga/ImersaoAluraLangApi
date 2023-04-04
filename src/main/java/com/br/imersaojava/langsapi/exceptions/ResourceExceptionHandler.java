package com.br.imersaojava.langsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(LangNotFoundException.class)
    public ResponseEntity<?> langNotFound(LangNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(LangAlreadyExistsException.class)
    public ResponseEntity<?> langAlreadyExists(LangAlreadyExistsException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }
}

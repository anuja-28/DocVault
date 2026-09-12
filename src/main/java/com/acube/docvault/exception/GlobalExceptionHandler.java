package com.acube.docvault.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDocumentNotFoundException(DocumentNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DocumentAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleDocumentAccessDeniedException(DocumentAccessDeniedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)   
    public String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }
}
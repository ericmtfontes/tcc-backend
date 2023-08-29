package com.api.tcc.handler;

import com.api.tcc.exceptions.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ExceptionResponse> carNotFoundException(CarNotFoundException e, HttpServletRequest request){
        ExceptionResponse error = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "Resource not found", e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> carAlreadyExistsException(CarAlreadyExistsException e, HttpServletRequest request){
        ExceptionResponse error = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Resource not found", e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> userAlreadyExistsException(UserAlreadyExistsException e, HttpServletRequest request){
        ExceptionResponse error = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Resource not found", e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotRentedException.class)
    public ResponseEntity<ExceptionResponse> notRentedException(NotRentedException e, HttpServletRequest request){
        ExceptionResponse error = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                "Resource not found", e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

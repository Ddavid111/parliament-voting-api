package com.example.demo.exception;

import com.example.demo.dto.HibaResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidaciosException.class)
    public ResponseEntity<HibaResponse> handleValidaciosException(
            ValidaciosException exception) {

        HibaResponse response =
                new HibaResponse(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HibaResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {

        HibaResponse response =
                new HibaResponse(
                        "A kérés JSON formátuma vagy valamelyik mező értéke hibás."
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
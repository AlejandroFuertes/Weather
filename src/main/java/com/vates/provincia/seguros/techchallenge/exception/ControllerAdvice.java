package com.vates.provincia.seguros.techchallenge.exception;

import com.vates.provincia.seguros.techchallenge.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorDTO> cityNotFoundExceptionHandler(CityNotFoundException ex) {

        ErrorDTO error = ErrorDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorDTO> cityNotFoundExceptionHandler(CountryNotFoundException ex) {

        ErrorDTO error = ErrorDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity<ErrorDTO> cityNotFoundExceptionHandler(WeatherNotFoundException ex) {

        ErrorDTO error = ErrorDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, ex.getStatus());
    }
}

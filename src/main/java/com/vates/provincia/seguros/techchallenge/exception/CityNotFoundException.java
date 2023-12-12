package com.vates.provincia.seguros.techchallenge.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CityNotFoundException extends RuntimeException{

    private final HttpStatus status;

    public CityNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

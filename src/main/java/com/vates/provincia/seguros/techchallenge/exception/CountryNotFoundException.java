package com.vates.provincia.seguros.techchallenge.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CountryNotFoundException extends RuntimeException{

    private final HttpStatus status;

    public CountryNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

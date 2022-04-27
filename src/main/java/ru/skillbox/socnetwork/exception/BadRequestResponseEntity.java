package ru.skillbox.socnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadRequestResponseEntity extends ResponseEntity {

    public BadRequestResponseEntity(String message) {
        super(new ErrorResponseDto(message), HttpStatus.BAD_REQUEST);
    }
}
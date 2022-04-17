package ru.skillbox.socnetwork.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadRequestResponseEntity extends ResponseEntity {

    public BadRequestResponseEntity(String message) {
        super(new ErrorResponseDto(message), HttpStatus.BAD_REQUEST);
    }
}
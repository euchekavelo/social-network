package ru.skillbox.socnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class VerifyTokenException extends RuntimeException {
    public VerifyTokenException(String message) {
        super(message);
    }
}

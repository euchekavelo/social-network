package ru.skillbox.socnetwork.controller.exeptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private String message;
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;

    public BadRequestException (String errorDescription) {
        super(errorDescription);
        this.error = "invalid_request";
        this.errorDescription = errorDescription;
    }
}

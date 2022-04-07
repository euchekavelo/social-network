package ru.skillbox.socnetwork.controller.exeptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponseDto {

    private String error;
    @JsonProperty("error_description")
    private String errorDescription;

    public ErrorResponseDto(String message) {
        error = "invalid_request";
        errorDescription = message;
    }

}

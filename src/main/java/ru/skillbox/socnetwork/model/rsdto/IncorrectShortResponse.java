package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IncorrectShortResponse {
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;

    public IncorrectShortResponse() {
        this.error = "invalid_request";
        this.errorDescription = "string";
    }
}
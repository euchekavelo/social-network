package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Properties;

@Data
public class GeneralResponse {
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    private long timestamp;
    private Properties data;

    public GeneralResponse(String error, long timestamp, Properties data) {
        this.error = error;
        this.timestamp = timestamp;
        this.data = data;
    }

    public GeneralResponse(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }
}

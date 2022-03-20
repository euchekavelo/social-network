package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeneralResponse<T> {
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    private long timestamp;
    private T data;

    public GeneralResponse(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public GeneralResponse(String error, long timestamp, T data) {
        this.error = error;
        this.timestamp = timestamp;
        this.data = data;
    }
}

package ru.skillbox.social_network.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Hashtable;

@Data
public class ProfileDto {

    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    private long timestamp;
    private Hashtable<String, Object> data;
}

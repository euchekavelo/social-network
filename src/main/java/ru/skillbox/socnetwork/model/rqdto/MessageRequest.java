package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequest {
    @JsonProperty("message_text")
    private String messageText;
}

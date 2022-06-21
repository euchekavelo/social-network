package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {
    @JsonProperty("message_text")
    private String messageText;
}

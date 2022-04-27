package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DialogDto {
    private int id;
    @JsonProperty("author_id")
    private Integer authorId;
    @JsonProperty("recipient_id")
    private Integer recipientId;
}

package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LastMessageResponse {
    private int id;
    private long time;
    @JsonProperty("author_id")
    private int authorId;
    @JsonProperty("recipient_id")
    private int recipientId;
    @JsonProperty("message_text")
    private String messageText;
    @JsonProperty("read_status")
    private String readStatus;
}

package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DialogsResponse {
    private int id;
    @JsonProperty("unread_count")
    private int unreadCount;
    @JsonProperty("last_message")
    private LastMessageResponse lastMessageResponse;
}

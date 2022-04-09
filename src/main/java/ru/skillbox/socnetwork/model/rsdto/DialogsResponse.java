package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DialogsResponse {
    private Integer id;
    @JsonProperty("unread_count")
    private Integer unreadCount;
    @JsonProperty("last_message")
    private MessageDto messageDto;
    private Integer count;
    private String message;

    public DialogsResponse(String message) {
        this.message = message;
    }

    public DialogsResponse(Integer count) {
        this.count = count;
    }

    public DialogsResponse() {

    }
}

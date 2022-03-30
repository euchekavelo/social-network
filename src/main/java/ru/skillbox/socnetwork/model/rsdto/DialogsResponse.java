package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DialogsResponse {
    private Integer id;
    @JsonProperty("unread_count")
    private Integer unreadCount;
    @JsonProperty("last_message")
    private LastMessageResponse lastMessageResponse;
//    private Integer count;
//    private String message;
    /**
     * TODO Может удалить классы с одним полем и здесь добавить 2 поля?
     */
}

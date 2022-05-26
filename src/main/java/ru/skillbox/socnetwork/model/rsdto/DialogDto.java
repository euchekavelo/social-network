package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DialogDto {
    private int id;
    @JsonProperty("dialog_id")
    private Integer dialogId;
    private Long time;
    @JsonProperty("unread_count")
    private Integer unreadCount;
    @JsonProperty("message_text")
    private String messageText;
    @JsonProperty("read_status")
    private String readStatus;
    @JsonProperty("author_id")
    private Integer authorId;
    @JsonProperty("recipient_id")
    private Integer recipientId;
    @JsonProperty("message_id")
    private Integer messageId;
}

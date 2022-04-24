package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DialogsDto {
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
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("e_mail")
    private String eMail;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("last_online_time")
    private Long lastOnlineTime;
}

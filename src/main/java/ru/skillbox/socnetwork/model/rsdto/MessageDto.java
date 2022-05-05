package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {
    private Integer id;
    private PersonForDialogsDto author;
    private PersonForDialogsDto recipient;
    private Long time;
    @JsonProperty("isSentByMe")
    private boolean isSentByMe;
    @JsonProperty("message_text")
    private String messageText;
    @JsonProperty("read_status")
    private String readStatus;
    @JsonProperty("author_id")
    private Integer authorId;
    @JsonProperty("recipient_id")
    private Integer recipientId;


    public MessageDto(Integer id, PersonForDialogsDto author, PersonForDialogsDto recipient, Long time, boolean isSentByMe, String messageText, String readStatus) {
        this.id = id;
        this.author = author;
        this.recipient = recipient;
        this.time = time;
        this.isSentByMe = isSentByMe;
        this.messageText = messageText;
        this.readStatus = readStatus;
    }
    public MessageDto() {

    }
}

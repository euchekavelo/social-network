package ru.skillbox.socnetwork.model.rsdto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.skillbox.socnetwork.model.entity.TypeReadStatus;

@Data
@RequiredArgsConstructor
public class MessageResponse {
  private int id;
  private long time = System.currentTimeMillis();
  @JsonProperty("author_id")
  private int authorId = 0;
  @JsonProperty("recipient_id")
  private int recipientId = 0;
  @JsonProperty("message_text")
  private String messageText = "message text";
  @JsonProperty("read_status")
  private String readStatus = TypeReadStatus.READ.toString();
}

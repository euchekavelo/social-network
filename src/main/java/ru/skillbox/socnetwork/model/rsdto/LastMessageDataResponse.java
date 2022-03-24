package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socnetwork.model.entity.Message;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastMessageDataResponse {
  private int id;
  @JsonProperty("unread_count")
  private int unreadCount = 0;
  @JsonProperty("last_message")
  private List<Message> lastMessage = new ArrayList<>();
}

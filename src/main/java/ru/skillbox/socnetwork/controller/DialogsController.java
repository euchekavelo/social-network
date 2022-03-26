package ru.skillbox.socnetwork.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.entity.Message;
import ru.skillbox.socnetwork.model.rsdto.CorrectLongResponse;
import ru.skillbox.socnetwork.model.rsdto.message.LastMessageDataResponse;

import java.util.List;
@RestController
@RequestMapping("/api/v1/dialogs")
public class DialogsController {

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getDialog() {
    CorrectLongResponse<LastMessageDataResponse> response = new CorrectLongResponse<LastMessageDataResponse>();
    response.setData(new LastMessageDataResponse());
    return ResponseEntity
        .ok(response);
  }

  @GetMapping(path = "/unreaded",produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getUnread() {
    CorrectLongResponse<LastMessageDataResponse> response = new CorrectLongResponse<LastMessageDataResponse>();
    LastMessageDataResponse lastMessageDataResponse = new LastMessageDataResponse();
    lastMessageDataResponse.setLastMessage(List.of(new Message()));
    response.setData(lastMessageDataResponse);
    return ResponseEntity
        .ok(response);
  }
}

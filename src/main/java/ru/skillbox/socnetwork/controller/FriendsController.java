package ru.skillbox.socnetwork.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendsController {

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getFriends() {
    return ResponseEntity.status(HttpStatus.OK).body(TempResponseDto.FRIENDS_RESPONSE);
  }

  @GetMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getAddFriendRequest() {
    return ResponseEntity.status(HttpStatus.OK).body(TempResponseDto.FRIENDS_RESPONSE);
  }

  @GetMapping(path = "/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getFriendsRecommendations() {
    return ResponseEntity.status(HttpStatus.OK).body(TempResponseDto.FRIENDS_RESPONSE);
  }

}

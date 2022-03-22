package ru.skillbox.socnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.dto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralListResponse;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;
import ru.skillbox.socnetwork.service.FriendsService;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendsController {

  private final FriendsService friendsService;

  @Autowired
  public FriendsController(FriendsService friendsService) {
    this.friendsService = friendsService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getFriends() {
    return ResponseEntity.ok(TempResponseDto.FRIENDS_RESPONSE);
  }

  @GetMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getAddFriendRequest() {
    return ResponseEntity.ok(TempResponseDto.FRIENDS_RESPONSE);
  }

  @GetMapping("/recommendations")
  public ResponseEntity<GeneralListResponse<PersonDto>> getListRecommendedFriends(
          @RequestParam(value = "offset", defaultValue = "0") int offset,
          @RequestParam(value = "itemPerPage", defaultValue = "20") int itemPerPage) {

    GeneralListResponse<PersonDto> generalListResponse =
            new GeneralListResponse<>(friendsService.getListRecommendedFriends(), offset, itemPerPage);

    return ResponseEntity.ok(generalListResponse);
  }
}

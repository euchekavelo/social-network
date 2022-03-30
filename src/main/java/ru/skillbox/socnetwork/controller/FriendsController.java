package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.PersonResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendsController {

    private final PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getFriends() {
        return ResponseEntity.ok(TempResponseDto.FRIENDS_RESPONSE);
    }

    @GetMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAddFriendRequest() {
        return ResponseEntity.ok(TempResponseDto.FRIENDS_RESPONSE);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<Object> getListRecommendedFriends(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "perPage", defaultValue = "20") int perPage) {
        List<PersonResponse> dataRespons = personService
                .getRecommendedFriendsList()
                .stream()
                .map(PersonResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                0,
                offset,
                perPage,
                dataRespons));
    }
}
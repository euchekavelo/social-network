package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.CorrectLongResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
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
    public ResponseEntity<CorrectLongResponse<List<PersonDataResponse>>> getListRecommendedFriends(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "perPage", defaultValue = "20") int perPage) {
        CorrectLongResponse<List<PersonDataResponse>> response = new CorrectLongResponse<>();
        List<PersonDataResponse> personDataResponses = personService
                .getRecommendedFriendsList()
                .stream()
                .map(PersonDataResponse::new)
                .collect(Collectors.toList());
        response.setData(personDataResponses);
        response.setOffset(offset);
        response.setPerPage(perPage);
        return ResponseEntity.ok(response);
    }
}
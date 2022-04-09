package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.controller.exception.InvalidRequestException;
import ru.skillbox.socnetwork.model.rsdto.*;
import ru.skillbox.socnetwork.service.FriendsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping("/request")
    public ResponseEntity<GeneralListResponse<PersonDto>> getListFriendRequests(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20") int itemPerPage) {
        GeneralListResponse<PersonDto> generalListResponse =
                new GeneralListResponse<>(friendsService.getListIncomingFriendRequests(), offset, itemPerPage);

        return ResponseEntity.ok(generalListResponse);
    }

    @PostMapping("/{id}")
    public ResponseEntity<GeneralResponse<DialogsResponse>> addFriend(@PathVariable Integer id)
            throws InvalidRequestException {
        GeneralResponse<DialogsResponse> generalResponse =
                new GeneralResponse<>("string", System.currentTimeMillis(), friendsService.addFriendById(id));

        return ResponseEntity.ok(generalResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<DialogsResponse>> deleteFriend(@PathVariable Integer id) {
        GeneralResponse<DialogsResponse> generalResponse =
                new GeneralResponse<>("string", System.currentTimeMillis(), friendsService.deleteFriendById(id));

        return ResponseEntity.ok(generalResponse);
    }

    @GetMapping
    public ResponseEntity<GeneralListResponse<PersonDto>> getUserFriends(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20") int itemPerPage) {
        GeneralListResponse<PersonDto> generalListResponse =
                new GeneralListResponse<>(friendsService.getUserFriends(), offset, itemPerPage);

        return ResponseEntity.ok(generalListResponse);
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
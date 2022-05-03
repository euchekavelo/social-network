package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.exception.InvalidRequestException;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.rqdto.PutLikeDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikedDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikesDto;
import ru.skillbox.socnetwork.service.LikeService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@InfoLogs
public class LikesController {


    private final LikeService likeService;

    @GetMapping(path = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikesDto>> getAllLikes(
            @RequestParam(value = "item_id") int itemId,
            @RequestParam(value = "type") String type)
            throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(likeService.getLikes(itemId, type)));
    }

    @PutMapping(path = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikesDto>> putAndGetAllLikes(
            @RequestBody PutLikeDto putLikeDto) throws InvalidRequestException {

            return ResponseEntity.ok(new GeneralResponse<>(likeService
                    .putAndGetAllLikes(putLikeDto.getItemId(), putLikeDto.getType())));
    }

    @GetMapping(path = "/liked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikedDto>> getIsLiked(
            @RequestParam(value = "user_id", defaultValue = "0") int userId,
            @RequestParam(value = "item_id") int itemId,
            @RequestParam(value = "type") String type) throws InvalidRequestException {

            return ResponseEntity.ok(new GeneralResponse<>(likeService.getLiked(itemId, type)));
    }


    @DeleteMapping(path = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikesDto>> deleteLikeAndGetAllLikes(
            @RequestParam(value = "item_id") int itemId,
            @RequestParam(value = "type") String type) throws InvalidRequestException {

            return ResponseEntity.ok(new GeneralResponse<>(likeService.deletePostLike(itemId, type)));
    }
}
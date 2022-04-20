package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.controller.exception.BadRequestResponseEntity;
import ru.skillbox.socnetwork.controller.exception.InvalidRequestException;
import ru.skillbox.socnetwork.model.rqdto.PutLikeDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikedDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikesDto;
import ru.skillbox.socnetwork.service.LikeService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
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
    public ResponseEntity<GeneralResponse<LikesDto>> putAndGetAllLikes(@RequestBody PutLikeDto putLikeDto) {

        String type = putLikeDto.getType();
        if (type.equals(POST)) {
            LikesDto likesDto = likeService
                    .putAndGetAllPostLikes(getSecurityUser().getId(), putLikeDto.getItemId());
            GeneralResponse<LikesDto> response = new GeneralResponse<>(likesDto);
            return ResponseEntity.ok(response);
        } else if (type.equals(COMMENT)) {
            return ResponseEntity.ok(new GeneralResponse<>());
        }
        return new BadRequestResponseEntity("wrong like type");
    }

    @GetMapping(path = "/liked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikedDto>> getIsLiked(
            @RequestParam(value = "user_id", defaultValue = "0") int userId,
            @RequestParam(value = "item_id") int itemId,
            @RequestParam(value = "type") String type) throws InvalidRequestException {

        if (type.equals(POST)) {
            GeneralResponse<LikedDto> response = new GeneralResponse<>(likeService.getPostLiked(itemId, type));
            return ResponseEntity.ok(response);
        } else if (type.equals(COMMENT)) {
            return ResponseEntity.ok(new GeneralResponse<>());
        }
        return new BadRequestResponseEntity("wrong like type");
    }


    @DeleteMapping(path = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikesDto>> deleteLikeAndGetAllLikes(@RequestParam(value = "item_id") int itemId,
                                                                              @RequestParam(value = "type") String type) {

        if (type.equals(POST)) {
            LikesDto likesDto = likeService.deletePostLike(getSecurityUser().getId(), itemId);
            GeneralResponse<LikesDto> response = new GeneralResponse<>(likesDto);
            return ResponseEntity.ok(response);
        } else if (type.equals(COMMENT)) {
            return ResponseEntity.ok(new GeneralResponse<>());
        }
        return new BadRequestResponseEntity("wrong like type");
    }
}
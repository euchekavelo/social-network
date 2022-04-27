package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.exception.BadRequestResponseEntity;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.rqdto.PutLikeDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikedDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikesDto;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.LikeService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@InfoLogs
public class LikesController {

    private final String POST = "Post";
    private final String COMMENT = "Comment";

    private final LikeService likeService;

    @GetMapping(path = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikesDto>> getAllLikes(@RequestParam(value = "item_id") int itemId,
                                                                 @RequestParam(value = "type") String type) {
        if (type.equals(POST)) {
            GeneralResponse<LikesDto> response = new GeneralResponse<>(likeService.getPostLikes(itemId));
            return ResponseEntity.ok(response);
        } else if (type.equals(COMMENT)) {
            return ResponseEntity.ok(new GeneralResponse<>());
        }
        return new BadRequestResponseEntity("wrong like type");
    }

    @PutMapping(path = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikesDto>> putAndGetAllLikes(@RequestBody PutLikeDto putLikeDto) {
        String type = putLikeDto.getType();
        if (type.equals(POST)) {
            LikesDto likesDto = likeService.putAndGetAllPostLikes(getSecurityUser().getId(), putLikeDto.getItemId());
            GeneralResponse<LikesDto> response = new GeneralResponse<>(likesDto);
            return ResponseEntity.ok(response);
        } else if (type.equals(COMMENT)) {
            return ResponseEntity.ok(new GeneralResponse<>());
        }
        return new BadRequestResponseEntity("wrong like type");
    }

    @GetMapping(path = "/liked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<LikedDto>> getIsLiked(@RequestParam(value = "user_id", defaultValue = "0") int userId,
                                                                @RequestParam(value = "item_id") int itemId,
                                                                @RequestParam(value = "type") String type) {
        if (type.equals(POST)) {
            GeneralResponse<LikedDto> response = new GeneralResponse<>(likeService.getPostLiked(getSecurityUser().getId(), itemId));
            return ResponseEntity.ok(response);
        } else if (type.equals(COMMENT)) {
            return ResponseEntity.ok(new GeneralResponse<>());
        }
        return new BadRequestResponseEntity("wrong like type");
    }

    private SecurityUser getSecurityUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) auth.getPrincipal();
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
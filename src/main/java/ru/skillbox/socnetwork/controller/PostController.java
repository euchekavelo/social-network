package ru.skillbox.socnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;

@RestController
@RequestMapping("/api/v1/post/")
public class PostController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllPost() {
        return ResponseEntity.status(HttpStatus.OK).body(TempResponseDto.POSTS_FEEDS_RESPONSE);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(TempResponseDto.POSTS_FEEDS_RESPONSE);
    }

    @GetMapping(path = "{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCommentsByPostId(@PathVariable int id) {
        return ResponseEntity.ok(TempResponseDto.COMMENTS_TO_POST_RESPONSE);
    }
}

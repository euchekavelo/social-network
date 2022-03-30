package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.postdto.CommentDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post/")
public class PostController {

    private final PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<List<PostDto>>> getAllPost(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                     @RequestParam(value = "perPage", defaultValue = "20") int perPage) {
        GeneralResponse<List<PostDto>> response = new GeneralResponse<>(postService.getALl(offset, perPage));
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<PostDto>> getPostById(@PathVariable int id) {
        GeneralResponse<PostDto> response = new GeneralResponse<>(postService.getById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCommentsByPostId(@PathVariable int id) {
        GeneralResponse<List<CommentDto>> response = new GeneralResponse<>(postService.getCommentDtoList(id));
        return ResponseEntity.ok(response);
    }
}

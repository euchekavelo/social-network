package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feeds")

public class FeedsController {

    private final PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<List<PostDto>>> getFeeds(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(value = "perPage", defaultValue = "20") int perPage) {

        return ResponseEntity.ok(new GeneralResponse<>(postService.getAll(offset, perPage)));
    }
}

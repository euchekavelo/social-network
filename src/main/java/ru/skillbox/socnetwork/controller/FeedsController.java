package ru.skillbox.socnetwork.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.service.FeedsService;

@RestController
@RequestMapping("/api/v1/feeds")
public class FeedsController {

    private final FeedsService feedsService;

    public FeedsController(FeedsService feedsService) {
        this.feedsService = feedsService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getFeeds() {
        return ResponseEntity.ok(feedsService.getFeeds());
    }


}

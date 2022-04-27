package ru.skillbox.socnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;

@RestController
@RequestMapping("/api/v1/platform/")
@InfoLogs
public class PlatformController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "languages")
    public ResponseEntity<Object> getAllPost() {
        return ResponseEntity.status(HttpStatus.OK).body(TempResponseDto.POSTS_FEEDS_RESPONSE);
    }
}

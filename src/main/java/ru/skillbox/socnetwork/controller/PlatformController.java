package ru.skillbox.socnetwork.controller;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;

@RestController
@RequestMapping("/api/v1/platform/")
public class PlatformController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "languages")
    public ResponseEntity<GeneralResponse<Map<String, Object>>> getAllPost() {
        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            TempResponseDto.LANGUAGES));
    }
}

package ru.skillbox.socnetwork.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.rsdto.*;
@RestController
@RequestMapping("/api/v1/dialogs")
@InfoLogs
public class DialogsController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<DialogsResponse>> getDialog() {
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                0,
                0,
                20,
                new DialogsResponse(
                        1,
                        0,
                        new LastMessageDto(
                                12,
                                System.currentTimeMillis(),
                                1,
                                1,
                                "string",
                                "SENT"))));
    }

    @GetMapping(path = "/unreaded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<Count>> getUnread() {
        return ResponseEntity
                .ok(new GeneralResponse<>(
                        "string",
                        System.currentTimeMillis(),
                        new Count(1000)));
    }
}

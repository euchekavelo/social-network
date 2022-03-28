package ru.skillbox.socnetwork.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.entity.Message;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.DataResponse;
import ru.skillbox.socnetwork.model.rsdto.LastMessageResponse;

import java.util.List;
@RestController
@RequestMapping("/api/v1/dialogs")
public class DialogsController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<DataResponse>> getDialog() {
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                0,
                0,
                20,
                List.of(new DataResponse(
                        1,
                        0,
                        new LastMessageResponse(
                                12,
                                System.currentTimeMillis(),
                                1,
                                1,
                                "string",
                                "SENT")))));
    }

    @GetMapping(path = "/unreaded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<DataResponse>> getUnread() {
        return ResponseEntity
                .ok(new GeneralResponse<>(
                        "string",
                        System.currentTimeMillis(),
                        List.of(new DataResponse(1000))));
    }
}

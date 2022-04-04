package ru.skillbox.socnetwork.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.*;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.DialogsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dialogs")
public class DialogsController {

    private final DialogsService dialogsService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialog(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0", required = false) Integer offset,
            @RequestParam(defaultValue = "20", required = false) Integer itemPerPage) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return dialogsService.getDialogs(email);
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

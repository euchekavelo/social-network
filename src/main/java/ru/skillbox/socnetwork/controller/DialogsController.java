package ru.skillbox.socnetwork.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialog() {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return dialogsService.getDialogs(securityUser.getId());
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<GeneralResponse<List<MessageDto>>> getDialogsMessageList(@PathVariable Integer id) {

        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return dialogsService.getMessageById(id);
    }

    @GetMapping(path = "/unreaded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<DialogsResponse>> getUnread() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return dialogsService.getUnreadMessageCount(securityUser.getId());
    }
}

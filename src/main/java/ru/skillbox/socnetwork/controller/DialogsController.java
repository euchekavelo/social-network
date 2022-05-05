package ru.skillbox.socnetwork.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rqdto.DialogRequest;
import ru.skillbox.socnetwork.model.rqdto.MessageRequest;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.rsdto.*;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.DialogsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dialogs")
@InfoLogs
public class DialogsController {

    private final DialogsService dialogsService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialog() {
        return dialogsService.getDialogs();
    }

    @PostMapping
    public ResponseEntity<GeneralListResponse<MessageDto>> createDialog(@RequestBody DialogRequest request) {

        return dialogsService.createDialog(request.getUserIds());
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<GeneralResponse<List<MessageDto>>> getDialogsMessageList(@PathVariable Integer id) {
        return dialogsService.getMessageById(id);
    }

    @GetMapping(path = "/unreaded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<DialogsResponse>> getUnread() {
        return dialogsService.getUnreadMessageCount();
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<GeneralResponse<MessageDto>> sendMessage(@RequestBody MessageRequest messageRequest, @PathVariable Integer id) {
        return dialogsService.sendMessage(messageRequest, id);
    }
}

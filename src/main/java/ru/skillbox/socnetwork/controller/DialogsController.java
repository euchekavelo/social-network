package ru.skillbox.socnetwork.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
        return ResponseEntity.ok(dialogsService.getDialogs());
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<DialogDto>> createDialog(@RequestBody DialogRequest request) {

        return ResponseEntity.ok(dialogsService.createDialog(request.getUserIds()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<DialogDto>> deleteDialog(@PathVariable Integer id) {
        return ResponseEntity.ok(dialogsService.deleteDialogByById (id));
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<GeneralResponse<List<MessageDto>>> getDialogsMessageList(@PathVariable Integer id) {
        return ResponseEntity.ok(dialogsService.getMessageById(id));
    }

    @GetMapping("/unreaded")
    public ResponseEntity<GeneralResponse<DialogsResponse>> getUnread() {
        return ResponseEntity.ok(dialogsService.getUnreadMessageCount());
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<GeneralResponse<MessageDto>> sendMessage(
            @RequestBody MessageRequest messageRequest, @PathVariable Integer id) {
        if (!messageRequest.getMessageText().equals("")) {
            return ResponseEntity.ok(dialogsService.sendMessage(messageRequest, id));
        }
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public MessageDto message(MessageDto message) {
        if (!message.getMessageText().equals("")) {
            //return ResponseEntity.ok(dialogsService.sendMessage(messageRequest, id));
        }
        return message;
    }
}

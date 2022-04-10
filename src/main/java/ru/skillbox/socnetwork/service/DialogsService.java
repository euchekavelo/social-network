package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.MessageDto;
import ru.skillbox.socnetwork.repository.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final MessageRepository messageRepository;

    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialogs(Integer id) {
        List<DialogsResponse> dialogList = messageRepository.getDialogList(id);

        if (id == null) {
            return ResponseEntity.badRequest().body(
                    new GeneralResponse<>("invalid_request", "string"));
        }
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(),
                    dialogList.size(), 0, 20, dialogList));
        }

        return ResponseEntity.status(401).body(
                new GeneralResponse<>("invalid_request", "string"));
    }
    public ResponseEntity<GeneralResponse<List<MessageDto>>> getMessageById(Integer id) {
        List<MessageDto> messageList = messageRepository.getMessageList(id);

        if (id == null) {
            return ResponseEntity.badRequest().body(
                    new GeneralResponse<>("invalid_request", "string"));
        }
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(),
                    messageList.size(), 0, 20, messageList));
        }

        return ResponseEntity.status(401).body(
                new GeneralResponse<>("invalid_request", "string"));
    }
}

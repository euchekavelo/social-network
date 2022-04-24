package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.enums.TypeReadStatus;
import ru.skillbox.socnetwork.model.rsdto.*;
import ru.skillbox.socnetwork.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final MessageRepository messageRepository;

    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialogs(Integer id) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            DialogsResponse dialogsResponse = null;
            List<DialogsDto> dialogList = messageRepository.getDialogList(id);
            List<DialogsResponse> dialogsResponseList = new ArrayList<>();

            for (DialogsDto dto : dialogList) {
                PersonForDialogsDto recipient = new PersonForDialogsDto(dto.getRecipientId(), dto.getPhoto(),
                        dto.getFirstName(),dto.getLastName(), dto.getEMail(), dto.getLastOnlineTime());
                dialogsResponse = new DialogsResponse();

                dialogsResponse.setId(dto.getDialogId());
                dialogsResponse.setRecipient(recipient);
                dialogsResponse.setMessageDto(new MessageDto(dto.getMessageId(),
                        messageRepository.getPersonForDialog(dto.getAuthorId()), recipient, dto.getTime(),
                        id == dto.getAuthorId(), dto.getMessageText(), dto.getReadStatus()));
                dialogsResponse.setUnreadCount(dto.getUnreadCount());
                dialogsResponseList.add(dialogsResponse);
            }
            return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(),
                    dialogList.size(), 0, 0, dialogsResponseList));
        }

        return ResponseEntity.status(401).body(
                new GeneralResponse<>("invalid_request", "string"));
    }
    public ResponseEntity<GeneralResponse<List<MessageDto>>> getMessageById(Integer id) {
        List<MessageDto> messageList = messageRepository.getMessageList(id);

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(),
                    messageList.size(), 0, 20, messageList));
        }

        return ResponseEntity.status(401).body(
                new GeneralResponse<>("invalid_request", "string"));
    }
    public ResponseEntity<GeneralResponse<DialogsResponse>> getUnreadMessageCount(Integer id) {

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(), messageRepository.getUnreadCount(id)));
        }

        return ResponseEntity.status(401).body(
                new GeneralResponse<>("invalid_request", "string"));
    }
}

package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.skillbox.socnetwork.model.entity.enums.TypeReadStatus;
import ru.skillbox.socnetwork.model.rqdto.MessageRequest;
import ru.skillbox.socnetwork.model.rsdto.*;
import ru.skillbox.socnetwork.repository.MessageRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final MessageRepository messageRepository;

    public ResponseEntity<GeneralResponse<MessageDto>> sendMessage(MessageRequest messageRequest, Integer id) {
        SecurityUser securityUser = (ru.skillbox.socnetwork.security.SecurityUser) SecurityContextHolder.getContext();

        return null;
    }

    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialogs() {
        SecurityUser securityUser = (ru.skillbox.socnetwork.security.SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            DialogsResponse dialogsResponse = null;
            List<DialogsDto> dialogList = messageRepository.getDialogList(securityUser.getId());
            List<DialogsResponse> dialogsResponseList = new ArrayList<>();

            for (DialogsDto dto : dialogList) {
                MessageDto authorIdAndRecipientId = messageRepository.getAuthorAndRecipientById(dto.getMessageId());
                boolean isSendByMe = securityUser.getId() == authorIdAndRecipientId.getAuthorId();
                PersonForDialogsDto recipient = null;


                if (isSendByMe) {
                    recipient = messageRepository.getPersonForDialog(authorIdAndRecipientId.getRecipientId());
                } else {
                    recipient = messageRepository.getPersonForDialog(authorIdAndRecipientId.getAuthorId());
                }
                dialogsResponse = new DialogsResponse();

                dialogsResponse.setId(dto.getDialogId());
                dialogsResponse.setRecipient(recipient);
                dialogsResponse.setMessageDto(new MessageDto(dto.getMessageId(),
                        messageRepository.getPersonForDialog(authorIdAndRecipientId.getAuthorId()), recipient, dto.getTime(),
                        isSendByMe, dto.getMessageText(), dto.getReadStatus()));
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
    public ResponseEntity<GeneralResponse<DialogsResponse>> getUnreadMessageCount() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(), messageRepository.getUnreadCount(securityUser.getId())));
        }

        return ResponseEntity.status(401).body(
                new GeneralResponse<>("invalid_request", "string"));
    }
}

package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rqdto.MessageRequest;
import ru.skillbox.socnetwork.model.rsdto.*;
import ru.skillbox.socnetwork.repository.DialogRepository;
import ru.skillbox.socnetwork.repository.MessageRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;

    public ResponseEntity<GeneralListResponse<MessageDto>> createDialog (List<Integer> userList) {
        SecurityUser securityUser = (ru.skillbox.socnetwork.security.SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        dialogRepository.createDialog(securityUser.getId());
        Integer dialogId = dialogRepository.getDialogIdByPerson(securityUser.getId()).getDialogId();
                System.out.println(dialogId);
        for (Integer id : userList) {
            dialogRepository.addPersonDialog(id, dialogId);
        }
        return null;
    }
    public ResponseEntity<GeneralResponse<MessageDto>> sendMessage (MessageRequest messageRequest, Integer dialog_id) {
        SecurityUser securityUser = (ru.skillbox.socnetwork.security.SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        messageRepository.sendMessage (System.currentTimeMillis(), securityUser.getId(),
                dialogRepository.getRecipientIdByDialogIdAndAuthorId(dialog_id, securityUser.getId()).getId(),
                messageRequest.getMessageText(), dialog_id);
        return null;
    }

    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialogs() {
        SecurityUser securityUser = (ru.skillbox.socnetwork.security.SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            List<DialogDto> dialogList = messageRepository.getDialogList(securityUser.getId());
            DialogsResponse dialogsResponse = null;
            List<DialogsResponse> dialogsResponseList = new ArrayList<>();

            for (DialogDto dto : dialogList) {
                PersonForDialogsDto author = messageRepository.getAuthorByMessageId(dto.getMessageId());
                PersonForDialogsDto recipient = messageRepository.getRecipientByMessageId (dto.getMessageId());
                boolean isSendByMe = securityUser.getId() == author.getId();
                dialogsResponse = new DialogsResponse();

                dialogsResponse.setId(dto.getDialogId());
                dialogsResponse.setRecipient(recipient);
                dialogsResponse.setMessageDto(new MessageDto(dto.getMessageId(),
                        author, recipient, dto.getTime(),
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
        SecurityUser securityUser = (ru.skillbox.socnetwork.security.SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<MessageDto> messageList = messageRepository.getMessageList(id);

        if (messageList.stream().anyMatch(a -> a.getReadStatus().equals("SENT"))) {
            messageRepository.updateReadStatus(id);
        }
        List<MessageDto> messageDtoList = new ArrayList<>();
        MessageDto messageDto = null;
        for (MessageDto dto : messageList) {
            boolean isSendByMe = securityUser.getId() == dto.getAuthorId();
            messageDto = new MessageDto();
            PersonForDialogsDto recipient = null;
            PersonForDialogsDto author = null;

            if (isSendByMe) {
                recipient = messageRepository.getPersonForDialog(dto.getRecipientId());
                author = messageRepository.getPersonForDialog(dto.getAuthorId());
            } else {
                author = messageRepository.getPersonForDialog(dto.getAuthorId());
                recipient = messageRepository.getPersonForDialog(dto.getRecipientId());
            }
            messageDto.setId(dto.getId());
            messageDto.setAuthor(author);
            messageDto.setRecipient(recipient);
            messageDto.setTime(dto.getTime());
            messageDto.setSentByMe(isSendByMe);
            messageDto.setMessageText(dto.getMessageText());
            messageDto.setReadStatus(dto.getReadStatus());
            messageDtoList.add(messageDto);
        }
        return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(),
                messageList.size(), 0, 10, messageDtoList));
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

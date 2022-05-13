package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.MessageDto;
import ru.skillbox.socnetwork.model.rqdto.MessageRequest;
import ru.skillbox.socnetwork.model.rsdto.*;
import ru.skillbox.socnetwork.repository.DialogRepository;
import ru.skillbox.socnetwork.repository.MessageRepository;
import ru.skillbox.socnetwork.security.SecurityUser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@DebugLogs
public class DialogsService {

    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;

    public GeneralResponse<DialogDto> createDialog (List<Integer> userList) {
        SecurityUser securityUser = getSecurityUser();
        Integer dialogId = 0;
        for (Integer recipientId : userList) {
            dialogId = dialogRepository.getDialogIdByPerson(securityUser.getId(), recipientId).getDialogId();
            if (dialogId == 0) {
                dialogId = dialogRepository.createDialog(securityUser.getId(), recipientId);
            }
        }
        DialogDto dialogDto = new DialogDto();
        dialogDto.setId(dialogId);
        return new GeneralResponse<>("string", System.currentTimeMillis(), dialogDto);
    }
    public GeneralResponse<MessageDto> sendMessage (MessageRequest messageRequest, Integer dialogId) {
        SecurityUser securityUser = getSecurityUser();
        DialogDto recipient = dialogRepository.getRecipientIdByDialogIdAndAuthorId(dialogId, securityUser.getId());
        Integer recipientDialogId = dialogRepository.getDialogIdByPerson(recipient.getId(), securityUser.getId()).getDialogId();
        Long time = System.currentTimeMillis();
        if (recipientDialogId == 0) {
            dialogRepository.createDialogForMessage(recipient.getId(), securityUser.getId(), dialogId);
        }
        Integer messageId = messageRepository.sendMessage (time, securityUser.getId(),
                recipient.getId(),
                messageRequest.getMessageText(), dialogId);
        return new GeneralResponse<>("String", time,
                new MessageDto(messageId, time, securityUser.getId(), recipient.getRecipientId(),
                        messageRequest.getMessageText(), "SENT"));
    }

    public GeneralResponse<List<DialogsResponse>> getDialogs() {
        SecurityUser securityUser = getSecurityUser();
        List<DialogDto> dialogList = dialogRepository.getDialogList(securityUser.getId());
        DialogsResponse dialogsResponse = null;
        List<DialogsResponse> dialogsResponseList = new ArrayList<>();
        PersonForDialogsDto recipient = null;
        PersonForDialogsDto author = null;

        for (DialogDto dto : dialogList) {

            recipient = dialogRepository.getRecipientBydialogId(dto.getDialogId(), securityUser.getId());
            author = dialogRepository.getAuthorByDialogId(dto.getDialogId(), securityUser.getId());

            boolean isSendByMe = securityUser.getId() == dto.getAuthorId();
            dialogsResponse = new DialogsResponse();
            dialogsResponse.setId(dto.getDialogId());
            dialogsResponse.setRecipient(recipient);
            dialogsResponse.setMessageDto(new MessageDto(dto.getMessageId(),
                    author, recipient, dto.getTime(),
                    isSendByMe, dto.getMessageText(), dto.getReadStatus()));
            dialogsResponse.setUnreadCount(dto.getUnreadCount());
            dialogsResponseList.add(dialogsResponse);
        }

        return new GeneralResponse<>("string", System.currentTimeMillis(),
                dialogList.size(), 0, 0, dialogsResponseList);
    }
    public GeneralResponse<List<MessageDto>> getMessageById(Integer id) {
        SecurityUser securityUser = getSecurityUser();
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
        return new GeneralResponse<>("string", System.currentTimeMillis(),
                messageList.size(), 0, 10, messageDtoList);
    }
    public GeneralResponse<DialogsResponse> getUnreadMessageCount() {
        SecurityUser securityUser = getSecurityUser();
        return new GeneralResponse<>("string", System.currentTimeMillis(),
                messageRepository.getUnreadCount(securityUser.getId()));
    }

    private SecurityUser getSecurityUser() {
        return (ru.skillbox.socnetwork.security.SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}

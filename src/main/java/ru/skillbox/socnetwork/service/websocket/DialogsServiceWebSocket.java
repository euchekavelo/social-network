package ru.skillbox.socnetwork.service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsDto;
import ru.skillbox.socnetwork.model.rsdto.MessageDto;
import ru.skillbox.socnetwork.model.rsdto.PersonForDialogsDto;
import ru.skillbox.socnetwork.repository.DialogRepository;
import ru.skillbox.socnetwork.repository.MessageRepository;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.service.SecurityPerson;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class DialogsServiceWebSocket {

    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;
    private final PersonRepository personRepository;
    private final SecurityPerson securityPerson = new SecurityPerson();

    public DialogDto deleteDialogByById (Integer id) {
        DialogDto dto = new DialogDto();

        dto.setId(dialogRepository.deleteDialog(id, securityPerson.getPersonId()));

        return dto;
    }
    public DialogDto createDialog (List<Integer> userList) {

        Integer dialogId = 0;
        Integer dialogCount = 0;
        Integer recipientId = userList.get(0);
        dialogCount = dialogRepository.dialogCountByAuthorIdAndRecipientId(recipientId, securityPerson.getPersonId()).getDialogId();
        if (dialogCount == 0) {
            dialogId = dialogRepository.createDialog(securityPerson.getPersonId(), recipientId);
        } else {
            dialogId = dialogRepository.createDialogForMessage(securityPerson.getPersonId(), recipientId,
                    dialogRepository.getDialogIdByPerson(recipientId, securityPerson.getPersonId()).getDialogId());
        }
        DialogDto dialogDto = new DialogDto();
        dialogDto.setId(dialogId);
        return dialogDto;
    }
    public MessageDto sendMessage (String messageRequest, Integer dialogId, String email) {
        Integer userId = securityPerson.getPersonId();
        DialogDto recipient = dialogRepository.getRecipientIdByDialogIdAndAuthorId(dialogId, userId);
        Integer recipientDialogId = dialogRepository.getDialogIdByPerson(recipient.getId(), userId).getDialogId();
        Long time = System.currentTimeMillis();
        if (recipientDialogId == 0) {
            dialogRepository.createDialogForMessage(recipient.getId(), userId, dialogId);
        }
        Integer messageId = messageRepository.sendMessage (time, userId,
                recipient.getId(),
                messageRequest, dialogId);
        return new MessageDto(messageId, time, userId, recipient.getRecipientId(),
                        messageRequest, "SENT");
    }

    public List<DialogsDto> getDialogs() {

        List<DialogDto> dialogList = dialogRepository.getDialogList(securityPerson.getPersonId());
        DialogsDto dialogsResponse = null;
        List<DialogsDto> dialogsResponseList = new ArrayList<>();
        PersonForDialogsDto recipient = null;
        PersonForDialogsDto author = null;

        for (DialogDto dto : dialogList) {

            recipient = dialogRepository.getRecipientBydialogId(dto.getDialogId(), securityPerson.getPersonId());
            author = dialogRepository.getAuthorByDialogId(dto.getDialogId(), securityPerson.getPersonId());

            boolean isSendByMe = securityPerson.getPersonId() == dto.getAuthorId();
            dialogsResponse = new DialogsDto();
            dialogsResponse.setId(dto.getDialogId());
            dialogsResponse.setRecipient(recipient);
            dialogsResponse.setMessageDto(new MessageDto(dto.getMessageId(),
                    author, recipient, dto.getTime(),
                    isSendByMe, dto.getMessageText(), dto.getReadStatus()));
            dialogsResponse.setUnreadCount(dto.getUnreadCount());
            dialogsResponseList.add(dialogsResponse);
        }

        return dialogsResponseList;
    }
    public List<MessageDto> getMessageById(Integer id) {

        List<MessageDto> messageList = messageRepository.getMessageList(id);

        if (messageList.stream().anyMatch(a -> a.getReadStatus().equals("SENT"))) {
            messageRepository.updateReadStatus(id);
        }
        List<MessageDto> messageDtoList = new ArrayList<>();
        MessageDto messageDto = null;
        for (MessageDto dto : messageList) {
            boolean isSendByMe = securityPerson.getPersonId() == dto.getAuthorId();
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
        return messageDtoList;
    }
    public DialogsDto getUnreadMessageCount() {
        return messageRepository.getUnreadCount(securityPerson.getPersonId());
    }
}

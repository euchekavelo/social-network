package ru.skillbox.socnetwork.service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rsdto.*;
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

    public MessageDto sendMessage (String messageRequest, Integer dialogId, String email) {
        Integer userId = personRepository.getIdByEmail(email);
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
}

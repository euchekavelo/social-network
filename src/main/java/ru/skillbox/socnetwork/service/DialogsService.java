package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Message;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final MessageRepository messageRepository;

    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialogs(String email) {
        List<Message> dialogList = messageRepository.getLastMessageByEmail(email);
        List<DialogsResponse> dialogsResponseList = new ArrayList<>();
        return ResponseEntity.ok(new GeneralResponse<List<DialogsResponse>>(
                "string",
                System.currentTimeMillis(),
                dialogList.size(),
                0,
                20, dialogsResponseList));
    }
}

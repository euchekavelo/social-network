package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.repository.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final MessageRepository messageRepository;

    public ResponseEntity<GeneralResponse<List<DialogsResponse>>> getDialogs(Integer id) {
        List<DialogsResponse> dialogList = messageRepository.getDialogList(id);

        return ResponseEntity.ok(new GeneralResponse<>("string", System.currentTimeMillis(),
                dialogList.size(), 0, 20, dialogList));
    }
}

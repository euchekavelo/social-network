package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rsdto.CorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.TempResponseDto;
import ru.skillbox.socnetwork.model.rsdto.message.OkMessage;
import ru.skillbox.socnetwork.security.JwtTokenProvider;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final JwtTokenProvider tokenProvider;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getNotification(@RequestHeader("Authorization") String token) {
        if (tokenProvider.validateJwtToken(token)) {
            return ResponseEntity.ok(TempResponseDto.TAG_RESPONSE);
        }
        CorrectShortResponse<OkMessage> correctShortResponse = new CorrectShortResponse<>();
        correctShortResponse.setData(new OkMessage());
        return ResponseEntity.ok()
                .headers(HttpHeaders.EMPTY)
                .body(correctShortResponse);
    }
}

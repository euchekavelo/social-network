package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;

import java.util.Properties;

@RequiredArgsConstructor
@Service
public class AccountService {

    public ResponseEntity<GeneralResponse> getRegisterResponse (RegisterDto request) {

        if (request.getEmail().equals("")) {
            Properties properties = new Properties();
            properties.put("message", "ok");
            return ResponseEntity.ok(new GeneralResponse(
                    "string",
                    1559751301818L,
                    properties
            ));
        }
        return ResponseEntity
                .badRequest()
                .body(new GeneralResponse(
                "invalid_request",
                "string"));
    }
}

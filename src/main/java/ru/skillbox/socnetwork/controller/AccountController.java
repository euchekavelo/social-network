package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.CorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.IncorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.message.OkMessage;
import ru.skillbox.socnetwork.service.PersonService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final PersonService personService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto request) {
        Person person = personService.getPersonAfterRegistration(request);
        if (person.getEmail() == null) {
            return errorResponse();
        }
        CorrectShortResponse<OkMessage> response = new CorrectShortResponse<>();
        response.setTimestamp(person.getRegDate().toLocalDate().toEpochDay());
        response.setData(new OkMessage());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> errorResponse() {
        return ResponseEntity
                .badRequest()
                .body(new IncorrectShortResponse());
    }
}

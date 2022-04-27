package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
@InfoLogs
public class AccountController {

    private final PersonService personService;

    @PostMapping(value = "/register")
    public ResponseEntity<GeneralResponse<DialogsResponse>> register(@RequestBody RegisterDto request) {
        Person person = personService.getPersonAfterRegistration(request);
        if (person == null) {
            return ResponseEntity
                .badRequest()
                .body(new GeneralResponse<>("invalid_request", "string"));
        }
        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            person.getRegDate(),
            new DialogsResponse("ok")));
    }

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<GeneralResponse<DialogsResponse>> recoverPassword(@RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse(personService.recoverPassword(body.get("email")))
        ));
    }

    @PutMapping(value = "/password/set")
    public ResponseEntity<GeneralResponse<DialogsResponse>> setPassword(@RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse(personService.setPassword(body))
        ));
    }

    @PutMapping(value = "/email/recovery")
    public ResponseEntity<GeneralResponse<DialogsResponse>> recoverEmail(
        @RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(
            new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                new DialogsResponse(personService.recoverEmail(body.get("email")))
            ));
    }

    @PutMapping(value = "/email")
    public ResponseEntity<GeneralResponse<DialogsResponse>> changeEmail(
        @RequestBody Map<String, String> body) throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse(personService.updateEmail(body))
        ));
    }
}

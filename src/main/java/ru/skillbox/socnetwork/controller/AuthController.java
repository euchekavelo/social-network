package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.CorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.IncorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
import ru.skillbox.socnetwork.service.PersonService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PersonService personService;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto request) {
        Person person = personService.getPersonAfterLogin(request);
        if (person == null) {
            return errorResponse();
        }
        String token = "";
        CorrectShortResponse<PersonDataResponse> response = new CorrectShortResponse<>();
        response.setTimestamp(System.currentTimeMillis());
        response.setData(new PersonDataResponse(person));
    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, token)
            .body(response);
    }

    private ResponseEntity<Object> errorResponse() {
        return ResponseEntity
                .badRequest()
                .body(new IncorrectShortResponse());
    }
}

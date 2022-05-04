package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.exception.InvalidRequestException;
import ru.skillbox.socnetwork.logging.InfoLogs;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.service.PersonService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@InfoLogs
public class AuthController {

    private final PersonService personService;

    @PostMapping(value = "/login")
    public ResponseEntity<GeneralResponse<PersonDto>> login(@RequestBody LoginDto loginDto) throws InvalidRequestException {

        return ResponseEntity.ok(new GeneralResponse<>(personService.getPersonAfterLogin(loginDto)));
    }

    /**
     * TODO build correct logout
     */
    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse<DialogsResponse>> logout() {

        return ResponseEntity.ok(new GeneralResponse<>(new DialogsResponse("ok")));
    }
}

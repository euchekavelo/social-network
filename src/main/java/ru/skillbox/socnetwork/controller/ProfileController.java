package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rsdto.CorrectLongResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.service.PersonService;


@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/")
public class ProfileController {

    private final PersonService personService;
    private final JwtTokenProvider tokenProvider;

    @GetMapping(path = "me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CorrectLongResponse<PersonDataResponse>> getMyProfile(@RequestHeader("Authorization") String token) {
        /**
         * TODO check method of excrete email from token
         */
        String email = tokenProvider.getEmailFromToken(token);
        PersonDataResponse personDataResponse = new PersonDataResponse(personService.getByEmail(email));
        return ResponseEntity.ok(getResponse(token, personDataResponse));
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CorrectLongResponse<PersonDataResponse>> getProfileById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        PersonDataResponse personDataResponse = new PersonDataResponse(personService.getById(id));
        return ResponseEntity.ok(getResponse(token, personDataResponse));
    }

    private CorrectLongResponse<PersonDataResponse> getResponse(String token, PersonDataResponse personDataResponse) {
        personDataResponse.setToken(token);
        CorrectLongResponse<PersonDataResponse> response = new CorrectLongResponse<>();
        response.setData(personDataResponse);
        return response;
    }
}

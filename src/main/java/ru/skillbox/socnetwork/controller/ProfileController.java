package ru.skillbox.socnetwork.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rsdto.CorrectLongResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
import ru.skillbox.socnetwork.security.jwt.JwtUtils;
import ru.skillbox.socnetwork.service.PersonService;


@Log
@RestController
@RequestMapping("/api/v1/users/")
public class ProfileController {

  @Autowired
  PersonService personService;
  @Autowired
  JwtUtils jwtUtils;

  @GetMapping(path = "me", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getMyProfile(@RequestHeader("Authorization") String token) {
    PersonDataResponse personDataResponse = personService.getByEmail(jwtUtils.getUserNameFromJwtToken(token));
    CorrectLongResponse<PersonDataResponse> response = getResponse(token, personDataResponse);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getProfileById(@PathVariable int id, @RequestHeader("Authorization") String token) {
    PersonDataResponse personDataResponse = personService.getById(id);
    CorrectLongResponse<PersonDataResponse> response = getResponse(token, personDataResponse);
    return ResponseEntity.ok(response);
  }

  private CorrectLongResponse<PersonDataResponse> getResponse(String token, PersonDataResponse personDataResponse) {
    personDataResponse.setToken(token);
    CorrectLongResponse<PersonDataResponse> response = new CorrectLongResponse<>();
    response.setData(personDataResponse);
    return response;
  }
}

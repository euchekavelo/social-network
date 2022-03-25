package ru.skillbox.socnetwork.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.CorrectLongResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
import ru.skillbox.socnetwork.security.jwt.JwtUtils;
import ru.skillbox.socnetwork.service.PersonService;

import javax.servlet.http.HttpServletRequest;

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
    Person person = personService.getByEmail(jwtUtils.getUserNameFromJwtToken(token));
    CorrectLongResponse<PersonDataResponse> response = getPersonDataResponseCorrectLongResponse(token, person);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getProfileById(@PathVariable int id, @RequestHeader("Authorization") String token
  ) {
    Person person = personService.getById(id);
    CorrectLongResponse<PersonDataResponse> response = getPersonDataResponseCorrectLongResponse(token, person);
    return ResponseEntity.ok(response);
  }

  private CorrectLongResponse<PersonDataResponse> getPersonDataResponseCorrectLongResponse(String token, Person person) {
    CorrectLongResponse<PersonDataResponse> response = new CorrectLongResponse<PersonDataResponse>();
    PersonDataResponse personDataResponse = new PersonDataResponse(person, token);
    response.setData(personDataResponse);
    return response;
  }
}

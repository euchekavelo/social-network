package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.DataResponse;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final PersonService personService;

    @PostMapping(value = "/register")
    public ResponseEntity<GeneralResponse<DataResponse>> register(@RequestBody RegisterDto request) {
        Person person = personService.getPersonAfterRegistration(request);
        if (person == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new GeneralResponse<>("invalid_request", "string"));
            //throw new BadRequestException("Invalid email or password for registration");
        }
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                person.getRegDate().toLocalDate().toEpochDay(),
                List.of(new DataResponse("ok"))));
    }
}

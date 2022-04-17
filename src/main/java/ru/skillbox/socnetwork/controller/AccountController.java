package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.TempToken;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.service.PersonService;
import ru.skillbox.socnetwork.service.TempTokenService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final PersonService personService;
    private final TempTokenService tempTokenService;

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

    @PostMapping(value = "/password/recovery")
    public ResponseEntity<GeneralResponse<DialogsResponse>> recoverPassword(@RequestBody Map<String, String> body){
        Person person = personService.getByEmail(body.get("email"));
        TempToken token = new TempToken(person.getEmail(), generateToken());
        tempTokenService.addToken(token);

        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse("ok")
        ));
    }

    @PutMapping(value = "/password/set")
    public ResponseEntity<GeneralResponse<DialogsResponse>> setPassword(@RequestBody Map<String, String> body){
        TempToken token = tempTokenService.getToken(body.get("token"));
        Person person = personService.getByEmail(token.getEmail());
        personService.updatePassword(body.get("password"), person);
        return ResponseEntity.ok(new GeneralResponse<>(
            "string",
            System.currentTimeMillis(),
            new DialogsResponse("ok")
        ));
    }

    private String generateToken(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(65, 90).build();
        return generator.generate(10);
    }
}

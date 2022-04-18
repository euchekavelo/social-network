package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.TempToken;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.MessageResponseDto;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.MailService;
import ru.skillbox.socnetwork.service.PersonService;
import ru.skillbox.socnetwork.service.TempTokenService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final PersonService personService;
    private final TempTokenService tempTokenService;
    private final MailService mailService;

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
    public ResponseEntity<GeneralResponse<MessageResponseDto>> recoverPassword(@RequestBody Map<String, String> body){
        Person person = personService.getByEmail(body.get("email"));
        TempToken token = new TempToken(person.getEmail(), generateToken());
        tempTokenService.addToken(token);
        String link = "localhost:8086/change-password?code=" + token.getToken();
        mailService.send(person.getEmail(), "SocNetwork Password recovery", link);
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                new MessageResponseDto("ok")
        ));
    }

    @PutMapping(value = "/password/set")
    public ResponseEntity<GeneralResponse<MessageResponseDto>> setPassword(@RequestParam String code, @RequestBody Map<String, String> body){
        if(code == null){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(
                    "invalid_request",
                    "string"
            ));
        }
        TempToken token = tempTokenService.getToken(body.get("code"));
        Person person = personService.getByEmail(token.getEmail());
        personService.updatePassword(body.get("password"), person);
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                new MessageResponseDto("ok")
        ));
    }

    @PutMapping(value = "/email")
    public ResponseEntity<GeneralResponse<MessageResponseDto>> changeEmail(@RequestBody Map<String, String> body){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        Person person = personService.getByEmail(securityUser.getUsername());
        personService.updateEmail(body.get("email"), person);
        return ResponseEntity.ok(new GeneralResponse<>(
                "string",
                System.currentTimeMillis(),
                new MessageResponseDto("ok")
        ));
    }

    private String generateToken(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(65, 90).build();
        return generator.generate(10);
    }
}

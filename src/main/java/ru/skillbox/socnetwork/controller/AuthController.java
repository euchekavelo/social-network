package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.service.PersonService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PersonService personService;
    private final JwtTokenProvider tokenProvider;


    @PostMapping(value = "/login")
    public ResponseEntity<GeneralResponse<PersonDto>> login(@RequestBody LoginDto loginDto) {

        return ResponseEntity.ok()
                .body(new GeneralResponse<>("string", System.currentTimeMillis(),
                        personService.getPersonAfterLogin(loginDto)));
    }

    /**
     * TODO build correct logout
     */
    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse<DialogsResponse>> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            auth.setAuthenticated(false);
            return ResponseEntity.ok()
                    .body(new GeneralResponse<>(
                            "string",
                            System.currentTimeMillis(),
                            new DialogsResponse("ok")));
        }
        return ResponseEntity.badRequest().body(new GeneralResponse<>(
                "invalid_request",
                "string"));
    }
}

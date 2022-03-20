package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.ProfileDto;
import ru.skillbox.socnetwork.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    //test l: petr@mail.ru p: 111111
    @PostMapping(value = "/login")
    public ResponseEntity<ProfileDto> login(@RequestBody LoginDto request) {

        if (request.getEmail().equals("") || request.getPassword().equals("")) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(authService.getLoginResponse(request));
    }
}

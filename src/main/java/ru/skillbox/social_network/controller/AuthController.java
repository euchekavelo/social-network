package ru.skillbox.social_network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.social_network.model.rqdto.LoginDto;
import ru.skillbox.social_network.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    //test l: petr@mail.ru p: 111111
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto request) {

        if (request.getEmail().equals("") || request.getPassword().equals("")) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(authService.getLoginResponse(request));
    }
}

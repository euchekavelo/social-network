package ru.skillbox.social_network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.social_network.model.rqdto.RegisterDto;
import ru.skillbox.social_network.model.rsdto.GeneralResponse;
import ru.skillbox.social_network.service.AccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    AccountService accountService;

    @PostMapping(value = "/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody RegisterDto request) {

        return accountService.getRegisterResponse(request);
    }
}

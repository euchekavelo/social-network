package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.Message;
import ru.skillbox.socnetwork.model.rsdto.PersonResponse;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PersonService personService;
    private final JwtTokenProvider tokenProvider;


    @PostMapping(value = "/login")
    public ResponseEntity<GeneralResponse<PersonResponse>> login(@RequestBody LoginDto loginDto) {

        return ResponseEntity.ok()
                .body(new GeneralResponse<>("string", System.currentTimeMillis(),
                        personService.getPersonAfterLogin(loginDto)));
    }

    /**
     * TODO build correct logout
     */
    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse<Message>> logout(
            HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, null);
            return ResponseEntity.ok()
                    .body(new GeneralResponse<>(
                            "string",
                            System.currentTimeMillis(),
                            new Message("ok")));
        }
        return ResponseEntity.badRequest().body(new GeneralResponse<>(
                "invalid_request",
                "string"));
        //throw new BadRequestException("bad logout");
    }
}

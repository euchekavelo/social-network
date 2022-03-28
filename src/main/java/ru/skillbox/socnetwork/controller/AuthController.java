package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.controller.exeptionhandler.BadRequestException;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.DataResponse;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PersonService personService;
    private final JwtTokenProvider tokenProvider;


    @PostMapping(value = "/login")
    public ResponseEntity<GeneralResponse<DataResponse>> login(@RequestBody LoginDto loginDto) {
        Person person = personService.getPersonAfterLogin(loginDto);
        if (person == null) {
            throw new BadRequestException("email not exist");
//            return errorResponse();
        }
/**
 *      Check for correct password
 */
        if (!loginDto.checkPassword(person.getPassword())) {
            throw new BadRequestException("wrong password");
        }
        String token = tokenProvider.generateToken(person.getEmail());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(new GeneralResponse<>(
                        "string",
                        System.currentTimeMillis(),
                        List.of(new DataResponse(person, token))));
    }

    /**
     * TODO build correct logout
     */
    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse<DataResponse>> logout(
            HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, null);
            return ResponseEntity.ok()
                    .body(new GeneralResponse<>(
                            "string",
                            System.currentTimeMillis(),
                            List.of(new DataResponse("ok"))));
        }
        return ResponseEntity.badRequest().body(new GeneralResponse<>(
                "invalid_request",
                "string"));
        //throw new BadRequestException("bad logout");
    }
}

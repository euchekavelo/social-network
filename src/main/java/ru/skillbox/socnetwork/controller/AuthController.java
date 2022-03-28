package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socnetwork.controller.exeptionhandler.BadRequestException;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.CorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.service.PersonService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PersonService personService;
    private final JwtTokenProvider tokenProvider;


    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
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
        CorrectShortResponse<PersonDataResponse> response = new CorrectShortResponse<>();
        response.setTimestamp(System.currentTimeMillis());
        response.setData(new PersonDataResponse(person, token));
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
//            .header(HttpHeaders.SET_COOKIE, token)
                .body(response);
    }

    /**
     * TODO build correct logout
     */
//    @PostMapping("/logout")
//    public ResponseEntity<GeneralListResponse<?>> logout(
//            HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//            return ResponseEntity.ok(new GeneralListResponse(
//                    "string",
//                    new Date().getTime(),
//                    List.of(new ForDataResponse("ok"))));
//        }
//        return ResponseEntity.badRequest()
//                .body(new GeneralListResponse(
//                        "invalid_request", "string"));
//    }
}

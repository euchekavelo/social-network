package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.DataResponse;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.security.jwt.JwtUtils;

@RequiredArgsConstructor
@Service
public class AuthService {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PersonService personService;

  @Autowired
  JwtUtils jwtUtils;

//  @PostMapping("/signin")
//  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//    Authentication authentication = authenticationManager
//        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
//
//    List<String> roles = userDetails.getAuthorities().stream()
//        .map(item -> item.getAuthority())
//        .collect(Collectors.toList());
//
//    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//        .body(new UserInfoResponse(userDetails.getId(),
//                                   userDetails.getUsername(),
//                                   userDetails.getEmail(),
//                                   roles));
//  }

  public ResponseEntity<?> getLoginResponse(LoginDto loginDto) {
    Person person = personService.getByEmail(loginDto.getEmail());
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    PersonDetailsImpl personDetails = (PersonDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(personDetails);

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new GeneralResponse(
        "string",
        System.currentTimeMillis(),
        new DataResponse(person)
    ));

  }

  public ResponseEntity<GeneralResponse> getLoginResponse() {
    LoginDto request = new LoginDto();
    request.setEmail("petr@mail.ru");
    request.setPassword("11111111");
    if (request.getEmail().equals("petr@mail.ru")
        && request.getPassword().equals("11111111")) {
      return ResponseEntity.ok(new GeneralResponse(
          "string",
          1559751301818L,
          new DataResponse(
              1,
              "Петр",
              "Петрович",
              1559751301818L,
              1559751301818L,
              "petr@mail.ru",
              "89100000000",
              "https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg",
              "Родился в небольшой, но честной семье",
              "Москва",
              "Россия",
              "ALL",
              1559751301818L,
              false,
              "1q2e3e3r4t5"
          )));
    }

    return getErrorResponse();
  }

  private ResponseEntity<GeneralResponse> getErrorResponse() {
    return ResponseEntity
        .badRequest()
        .body(new GeneralResponse(
            "invalid_request",
            "string"));
  }
}

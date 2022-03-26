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
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.CorrectLongResponse;
import ru.skillbox.socnetwork.model.rsdto.IncorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
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

  public ResponseEntity<?> getLoginResponse(LoginDto loginDto) {
    if (personService.isEmptyEmail(loginDto.getEmail())) {
      return errorResponse();
    }
    PersonDataResponse personDataResponse = personService.getByEmail(loginDto.getEmail());
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    PersonDetailsImpl personDetails = (PersonDetailsImpl) authentication.getPrincipal();
    String token = jwtUtils.generateTokenFromUsername(personDataResponse.getEmail());
    personDataResponse.setToken(token);
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(personDetails);
    CorrectLongResponse<PersonDataResponse> response = new CorrectLongResponse<>();
    response.setData(personDataResponse);
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
  }

  private ResponseEntity<?> errorResponse() {
    return ResponseEntity
        .badRequest()
        .body(new IncorrectShortResponse());
  }
}

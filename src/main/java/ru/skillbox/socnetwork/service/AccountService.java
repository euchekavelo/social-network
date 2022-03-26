package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.IncorrectShortResponse;

@RequiredArgsConstructor
@Service
public class AccountService {

    @Autowired
    PersonService personService;

    public ResponseEntity<?> getRegisterResponse (RegisterDto registerDto) {
        if (!registerDto.getFirstPassword().equals(registerDto.getSecondPassword()) || !personService.isEmptyEmail(registerDto.getEmail())) {
            return errorResponse();
        }
        Person person = new Person();
        person.setEmail(registerDto.getEmail());
        person.setPassword(new BCryptPasswordEncoder().encode(registerDto.getSecondPassword()));
        person.setFirstName(registerDto.getFirstName());
        person.setLastName(registerDto.getLastName());

        return ResponseEntity.ok(personService.saveFromRegistration(person));
    }

    private ResponseEntity<?> errorResponse() {
        return ResponseEntity
                .badRequest()
                .body(new IncorrectShortResponse());
    }
}
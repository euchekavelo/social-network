package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.controller.exeptionhandler.BadRequestException;
import ru.skillbox.socnetwork.controller.exeptionhandler.VerifyTokenException;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.security.JwtTokenProvider;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final JwtTokenProvider tokenProvider;

    public List<Person> getAll() {
        return personRepository.getAll();
    }

    public Person getByEmail(String email) {
        return personRepository.getByEmail(email);
    }

    public boolean isEmptyEmail(String email) {
        return personRepository.isEmptyEmail(email);
    }

    public Person saveFromRegistration(Person person) {
        return personRepository.saveFromRegistration(person);
    }

    public Person getById(int id) {
        return personRepository.getById(id);
    }

    public Person getPersonAfterRegistration(RegisterDto registerDto) {
        if (!registerDto.passwordsEqual() || !isEmptyEmail(registerDto.getEmail())) {
            return null;
        }
        //TODO вынести создание персона из RegisterDto в Person?
        Person person = new Person();
        person.setPhoto("https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg");
        person.setEmail(registerDto.getEmail());
        person.setPassword(new BCryptPasswordEncoder().encode(registerDto.getSecondPassword()));
        person.setFirstName(registerDto.getFirstName());
        person.setLastName(registerDto.getLastName());
        return saveFromRegistration(person);
    }

    public PersonDto getPersonAfterLogin(LoginDto loginDto) {
        Person person = personRepository.getPersonAfterLogin(loginDto);
        if (person == null) {
            throw new BadRequestException("user not found");
        } else {
        return new PersonDto(person,
                tokenProvider.generateToken(loginDto.getEmail()));
        }
    }
}

package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.repository.PersonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

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

    public List<Person> getRecommendedFriendsList() {
        return personRepository.getListRecommendedFriends();
    }

    public Person getPersonAfterRegistration(RegisterDto registerDto) {
        if (!registerDto.passwordsEqual() || !isEmptyEmail(registerDto.getEmail())) {
            return null;
        }
        Person person = new Person();
        person.setEmail(registerDto.getEmail());
        //TODO вынести new BCryptPasswordEncoder().encode(registerDto.getSecondPassword()) в Person?
        person.setPassword(new BCryptPasswordEncoder().encode(registerDto.getSecondPassword()));
        person.setFirstName(registerDto.getFirstName());
        person.setLastName(registerDto.getLastName());
        return saveFromRegistration(person);
    }

    public Person getPersonAfterLogin(LoginDto loginDto) {
        return personRepository.getPersonAfterLogin(loginDto);
    }
}

package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.CorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDataResponse;
import ru.skillbox.socnetwork.model.rsdto.message.OkMessage;
import ru.skillbox.socnetwork.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {
    private PersonRepository personRepository;

    public List<PersonDataResponse> getAll() {
        return personRepository.getListRecommendedFriends().stream()
                .map(PersonDataResponse::new)
                .collect(Collectors.toList());
    }

    public PersonDataResponse getByEmail(String email) {
        return new PersonDataResponse(personRepository.getByEmail(email));
    }

    public boolean isEmptyEmail(String email) {
        return personRepository.isEmptyEmail(email);
    }

    public CorrectShortResponse<OkMessage> saveFromRegistration(Person person) {
        return personRepository.saveFromRegistration(person);
    }

    public PersonDataResponse getById(int id) {
        return new PersonDataResponse(personRepository.getById(id));
    }

    public List<PersonDataResponse> getRecommendedFriendsList() {
         return personRepository.getListRecommendedFriends().stream()
                .map(PersonDataResponse::new)
                .collect(Collectors.toList());
    }
}

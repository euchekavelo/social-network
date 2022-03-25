package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rsdto.CorrectShortResponse;
import ru.skillbox.socnetwork.model.rsdto.OkMessage;
import ru.skillbox.socnetwork.repository.PersonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
  private PersonRepository personRepository;

  public List<Person> getAll() {
    return this.personRepository.getAll();
  }

  public Person getByEmail(String email) {
    return this.personRepository.getByEmail(email);
  }

  public boolean isEmptyEmail(String email) {return this.personRepository.isEmptyEmail(email);}

  public CorrectShortResponse<OkMessage> saveFromRegistration(Person person) {
    return personRepository.saveFromRegistration(person);
  }
}

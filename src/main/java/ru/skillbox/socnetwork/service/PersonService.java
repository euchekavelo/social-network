package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.repository.PersonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
  private PersonRepository userRepository;

  public List<Person> getAll() {
    return this.userRepository.getAll();
  }

  public Person getByEmail(String email) {
    return this.userRepository.getByEmail(email);
  }

  public Person getById(int id){
    return userRepository.getById(id);
  }
}

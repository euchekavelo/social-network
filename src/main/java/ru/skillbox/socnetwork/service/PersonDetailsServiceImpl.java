package ru.skillbox.socnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.repository.PersonRepository;

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private PersonRepository personRepository;

  @Override
  @Transactional
  public PersonDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
    Person person = personRepository.getByEmail(username);

    return PersonDetailsImpl.build(person);
  }

}

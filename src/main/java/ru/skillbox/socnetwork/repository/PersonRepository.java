package ru.skillbox.socnetwork.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.mapper.PersonMapper;


@RequiredArgsConstructor
@Repository
public class PersonRepository {

  private final JdbcTemplate jdbc;

  public Person getById(int id) {
    return jdbc.queryForObject("select * from person where id =" + id, new PersonMapper());
  }
}
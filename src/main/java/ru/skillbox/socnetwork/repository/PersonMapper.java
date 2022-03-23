package ru.skillbox.socnetwork.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

  @Override
  public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
    return null;
  }
}

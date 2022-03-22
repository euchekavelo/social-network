package ru.skillbox.socnetwork.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

  @Override
  public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
    Person person = new Person();
    person.setId(rs.getInt("id"));
    person.setFirstName(rs.getString("first_name"));
    person.setLastName(rs.getString("last_name"));
    person.setRegDate(rs.getDate("reg_date").getTime());
    person.setBirthDate(rs.getDate("birth_date").getTime());
    person.setEmail(rs.getString("e_mail"));
    person.setPhone(rs.getString("phone"));
    person.setPassword(rs.getString("password"));
    person.setPhoto(rs.getString("photo"));
    person.setAbout(rs.getString("about"));
    person.setCity(rs.getString("city"));
    person.setCountry(rs.getString("country"));
    person.setConfirmationCode(rs.getString("confirmation_code"));
    return person;
  }
}

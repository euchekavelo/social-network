package ru.skillbox.socnetwork.model.mapper;

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
    person.setLastOnlineTime(rs.getLong("last_online_time"));
    person.setConfirmationCode(rs.getString("confirmation_code"));

    //TODO   добавить по надобности остальные:
    //      confirmation_code varchar(20),
    //      is_approved boolean,
    //      messages_permission permission_type,
    //      is_blocked boolean,
    return person;
  }

}



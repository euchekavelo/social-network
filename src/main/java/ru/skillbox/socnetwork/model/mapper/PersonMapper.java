package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PersonMapper implements RowMapper<Person> {

  @Override
  public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
    Person mapper = new Person();
    mapper.setId(rs.getInt("id"));
    mapper.setFirstName(rs.getString("first_name"));
    mapper.setLastName(rs.getString("last_name"));
    mapper.setRegDate(getDateTime(rs,"reg_date"));
    mapper.setBirthDate(getDate(rs, "birth_date"));
    mapper.setEmail(rs.getString("e_mail"));
    mapper.setPhone(rs.getString("phone"));
    mapper.setPassword(rs.getString("password"));
    mapper.setPhoto(rs.getString("photo"));
    mapper.setAbout(rs.getString("about"));
    mapper.setCity(rs.getString("town"));
    mapper.setLastOnlineTime(getDateTime(rs,"last_online_time"));

    //TODO   добавить по надобности остальные:
    //      confirmation_code varchar(20),
    //      is_approved boolean,
    //      messages_permission permission_type,
    //      is_blocked boolean,
    return mapper;
  }

  private LocalDateTime getDateTime(ResultSet rs, String value) throws SQLException {
    Timestamp time = rs.getTimestamp(value);
    if (time != null) {
      return time.toLocalDateTime();
    }
    return LocalDateTime.now();
  }

  private LocalDate getDate(ResultSet rs, String value) throws SQLException {
    Timestamp time = rs.getTimestamp(value);
    if (time != null) {
      return time.toLocalDateTime().toLocalDate();
    }
    return LocalDate.now();
  }


}



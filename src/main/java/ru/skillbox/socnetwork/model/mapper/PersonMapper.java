package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.TypePermission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonMapper implements RowMapper<Person> {

  @Override
  public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
    Person mapper = new Person();
    mapper.setId(rs.getInt("id"));
    mapper.setFirstName(rs.getString("first_name"));
    mapper.setLastName(rs.getString("last_name"));
    mapper.setTimestamp(rs.getTimestamp("reg_date").toLocalDateTime());
    mapper.setBirthDate(rs.getTimestamp("birth_date").toLocalDateTime().toLocalDate());
    mapper.setEmail(rs.getString("e_mail"));
    mapper.setPhone(rs.getString("phone"));
    mapper.setPassword(rs.getString("password"));
    mapper.setPhoto(rs.getString("photo"));
    mapper.setAbout(rs.getString("about"));
    mapper.setTown(rs.getString("town"));
    mapper.setLastOnlineTime(rs.getTimestamp("last_online_time").toLocalDateTime());

    //TODO   добавить по надобности остальные:
    //      confirmation_code varchar(20),
    //      is_approved boolean,
    //      messages_permission permission_type,
    //      is_blocked boolean,
    return mapper;
  }
}



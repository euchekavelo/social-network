package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.enums.TypePermission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person mapper = new Person();
        mapper.setId(rs.getInt("id"));
        mapper.setFirstName(rs.getString("first_name"));
        mapper.setLastName(rs.getString("last_name"));
        mapper.setRegDate(rs.getLong("reg_date"));
        mapper.setBirthDate(rs.getLong("birth_date"));
        mapper.setEmail(rs.getString("e_mail"));
        mapper.setPhone(rs.getString("phone"));
        mapper.setPassword(rs.getString("password"));
        mapper.setPhoto(rs.getString("photo"));
        mapper.setAbout(rs.getString("about"));
        mapper.setCity(rs.getString("town"));
        mapper.setLastOnlineTime(rs.getLong("last_online_time"));
        mapper.setConfirmationCode(rs.getString("confirmation_code"));
        mapper.setIsApproved(rs.getBoolean("is_approved"));
        mapper.setMessagesPermission(getPermission(rs.getObject("messages_permission")));
        mapper.setIsBlocked(rs.getBoolean("is_blocked"));
        return mapper;
    }

    private TypePermission getPermission(Object object) {
        return object == null ? TypePermission.ALL : (TypePermission) object;
    }
}



package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.Notification;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification mapper = new Notification();
        mapper.setId(rs.getInt("id"));
        mapper.setTypeId(rs.getInt("type_id"));
        mapper.setSentTime(rs.getLong("sent_time"));
        mapper.setPersonId(rs.getInt("person_id"));
        mapper.setEntityId(rs.getInt("entity_id"));
        mapper.setContact(rs.getString("contact"));
        return mapper;
    }
}
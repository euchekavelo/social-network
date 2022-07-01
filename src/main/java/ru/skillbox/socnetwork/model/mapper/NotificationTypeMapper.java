package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.NotificationType;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;

import java.sql.ResultSet;
import java.sql.SQLException;
public class NotificationTypeMapper implements RowMapper<NotificationType> {
    @Override
    public NotificationType mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationType mapper = new NotificationType();
        mapper.setId(rs.getInt("id"));
        mapper.setName(rs.getString("name"));
        mapper.setCode(TypeNotificationCode.valueOf(rs.getString("code")));
        return mapper;
    }
}
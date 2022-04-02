package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.Message;
import ru.skillbox.socnetwork.model.entity.enums.TypeReadStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message mapper = new Message();
        mapper.setId(rs.getInt("id"));
        mapper.setTime(getLong(rs.getTimestamp("time")));
        mapper.setAuthorId(rs.getInt("author_id"));
        mapper.setRecipientId(rs.getInt("recipient_id"));
        mapper.setMessageText(rs.getString("message_text"));
        mapper.setReadStatus(TypeReadStatus.valueOf(rs.getString("read_status")));
        return mapper;
    }

    private Long getLong(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.getTime();
    }
}
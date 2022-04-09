package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.MessageDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DialogMapper implements RowMapper<DialogsResponse> {
    @Override
    public DialogsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        DialogsResponse dialogsResponseMapper = new DialogsResponse();

        dialogsResponseMapper.setId(rs.getInt("dialog_id"));
        dialogsResponseMapper.setUnreadCount(rs.getInt("unread_count"));
        dialogsResponseMapper.setMessageDto(
                new MessageDto(rs.getInt("id"), rs.getLong("time"),
                        rs.getInt("author_id"), rs.getInt("recipient_id"),
                        rs.getString("message_text"), rs.getString("read_status")));
        return dialogsResponseMapper;
    }

    private Long getLong(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.getTime();
    }
}

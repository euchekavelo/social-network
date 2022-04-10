package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.mapper.DialogMapper;
import ru.skillbox.socnetwork.model.mapper.MessageMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.MessageDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MessageRepository {
    private final JdbcTemplate jdbc;

    public List<DialogsResponse> getDialogList(Integer id) {
        String sql = "SELECT dialog_id, MAX(time) AS time, COUNT(*) AS unread_count, MAX(message_text) AS message_text, " +
                "MAX(read_status) AS read_status, MAX(author_id) AS author_id, MAX(recipient_id) AS recipient_id, " +
                "MAX(id) AS id FROM message WHERE read_status = 'SENT' AND recipient_id = ? GROUP BY dialog_id " +
                "UNION " +
                "SELECT dialog_id, MAX(time) AS time, 0 AS unread_count, MAX(message_text) AS message_text, " +
                "MAX(read_status) AS read_status, MAX(author_id) AS author_id, MAX(recipient_id) AS recipient_id, " +
                "MAX(id) AS id FROM message WHERE read_status = 'READ' AND recipient_id = ? GROUP BY dialog_id";
        return jdbc.query(sql, new DialogMapper(), id, id);
    }
    public List<MessageDto> getMessageList(Integer id) {
        String sql = "select id, time, author_id, recipient_id, message_text, read_status FROM message WHERE dialog_id = ?";
        return jdbc.query(sql, new MessageMapper(), id);
    }
}

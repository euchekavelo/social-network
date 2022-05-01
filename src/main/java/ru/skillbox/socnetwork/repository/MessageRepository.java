package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.mapper.*;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;
import ru.skillbox.socnetwork.model.rsdto.DialogsResponse;
import ru.skillbox.socnetwork.model.rsdto.MessageDto;
import ru.skillbox.socnetwork.model.rsdto.PersonForDialogsDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MessageRepository {
    private final JdbcTemplate jdbc;

    public void updateReadStatus (Integer id) {
        String sql = "UPDATE message SET read_status = 'READ' WHERE dialog_id = ? AND read_status = 'SENT'";
        jdbc.update(sql, id);
    }
    public void sendMessage (Long time, Integer author_id,
                             Integer recipient_id, String message_text, Integer dialog_id) {
        String sql = "INSERT INTO message (time, author_id, recipient_id, message_text, read_status, dialog_id) " +
                "VALUES (?, ?, ?, ?, 'SENT', ?)";
        jdbc.update(sql, time, author_id, recipient_id, message_text, dialog_id);
    }

    public PersonForDialogsDto getRecipientByMessageId (Integer id) {
        String sql = "SELECT person.id, photo, first_name, last_name, e_mail, last_online_time from message " +
                "INNER JOIN person ON recipient_id = person.id " +
                "WHERE message.id = ? ";

        return jdbc.queryForObject(sql, new PersonForDialogsMapper(), id);
    }
    public PersonForDialogsDto getAuthorByMessageId (Integer id) {
        String sql = "SELECT person.id, photo, first_name, last_name, e_mail, last_online_time from message " +
                "INNER JOIN person ON author_id = person.id " +
                "WHERE message.id = ?";

        return jdbc.queryForObject(sql, new PersonForDialogsMapper(), id);
    }

    public PersonForDialogsDto getPersonForDialog (Integer id) {
        String sql = "SELECT id, photo, first_name, last_name, e_mail, last_online_time FROM person WHERE id = ?";

        return jdbc.queryForObject(sql, new PersonForDialogsMapper(), id);
    }
    public List<DialogDto> getDialogList(Integer id) {
        String sql = "SELECT dialog.dialog_id, MAX(time) AS time, MAX(message_text) AS message_text, " +
                "MAX(read_status) AS read_status, MAX(message.id) AS message_id, " +
                "(SELECT COUNT(*) FROM message WHERE message.read_status = 'SENT' " +
                "AND message.dialog_id = dialog.dialog_id AND author_id <> ?) AS unread_count " +
                "FROM dialog " +
                "LEFT JOIN message ON message.dialog_id = dialog.dialog_id " +
                "WHERE person_id = ? " +
                "GROUP BY dialog.dialog_id";
        return jdbc.query(sql, new DialogsMapper(), id, id);
    }
    public List<MessageDto> getMessageList(Integer id) {
        String sql = "select id, time, author_id, recipient_id, message_text, read_status FROM message WHERE dialog_id = ?";
        return jdbc.query(sql, new MessageMapper(), id);
    }
    public DialogsResponse getUnreadCount(Integer id) {
        String sql = "SELECT COUNT(*) AS unread_count FROM message WHERE read_status = 'SENT' AND recipient_id = ?";

        return jdbc.queryForObject(sql, new CountMapper(), id);
    }
}

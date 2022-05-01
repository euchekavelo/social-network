package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.mapper.DialogIdMapper;
import ru.skillbox.socnetwork.model.mapper.DialogsMapper;
import ru.skillbox.socnetwork.model.mapper.PersonForDialogsMapper;
import ru.skillbox.socnetwork.model.mapper.RecipientMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;
import ru.skillbox.socnetwork.model.rsdto.PersonForDialogsDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DialogRepository {

    private final JdbcTemplate jdbc;

    public List<DialogDto> getDialogList(Integer id) {
        String sql = "SELECT dialog.dialog_id, MAX(time) AS time, " +
                "(SELECT message_text FROM message WHERE dialog_id = dialog.dialog_id ORDER BY time DESC LIMIT 1) AS message_text," +
                "(SELECT author_id FROM message WHERE dialog_id = dialog.dialog_id ORDER BY time DESC LIMIT 1) AS last_author_id," +
                "MAX(read_status) AS read_status, MAX(message.id) AS message_id, " +
                "(SELECT COUNT(*) FROM message WHERE message.read_status = 'SENT' " +
                "AND message.dialog_id = dialog.dialog_id AND author_id <> ?) AS unread_count " +
                "FROM dialog " +
                "LEFT JOIN message ON message.dialog_id = dialog.dialog_id " +
                "WHERE person_id = ? " +
                "GROUP BY dialog.dialog_id ORDER BY time DESC";
        return jdbc.query(sql, new DialogsMapper(), id, id);
    }

    public PersonForDialogsDto getAuthorByDialogId (Integer dialogId, Integer authorId) {
        String sql = "SELECT person.id, photo, first_name, last_name, e_mail, last_online_time " +
                "FROM dialog " +
                "INNER JOIN person ON person_id = person.id " +
                "WHERE dialog.dialog_id = ? AND dialog.person_id = ?";

        return jdbc.queryForObject(sql, new PersonForDialogsMapper(), dialogId, authorId);
    }

    //TODO доработать для групового чата
    public PersonForDialogsDto getRecipientBydialogId (Integer dialogId, Integer authorId) {
        String sql = "SELECT person.id, photo, first_name, last_name, e_mail, last_online_time " +
                "FROM dialog " +
                "INNER JOIN person ON person_id = person.id " +
                "WHERE dialog.dialog_id = ? AND dialog.person_id <> ?";

        return jdbc.queryForObject(sql, new PersonForDialogsMapper(), dialogId, authorId);
    }
    public void addPersonDialog(Integer userId, Integer dialogId) {
        String sql = "INSERT INTO dialog (person_id, dialog_id) " +
                "VALUES (?, ?)";
        jdbc.update(sql, userId, dialogId);
    }
    public void createDialog(Integer author_id) {
        String sql = "INSERT INTO dialog (person_id, dialog_id) " +
                "VALUES (?, (SELECT MAX(dialog_id) + 1 FROM dialog))";
        jdbc.update(sql, author_id);
    }

    public DialogDto getDialogIdByPerson (Integer id) {
        String sql = "SELECT MAX(dialog_id) AS dialog_id FROM dialog WHERE person_id = ?";
        return jdbc.queryForObject(sql, new DialogIdMapper(), id);
    }
    public DialogDto getRecipientIdByDialogIdAndAuthorId (Integer dialog_id, Integer author_id) {
        String sql = "SELECT person_id AS recipient_id FROM dialog WHERE dialog_id = ? AND person_id <> ?";

        return jdbc.queryForObject(sql, new RecipientMapper(), dialog_id, author_id);
    }
}

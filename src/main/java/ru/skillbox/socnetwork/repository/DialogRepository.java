package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.mapper.DialogIdMapper;
import ru.skillbox.socnetwork.model.mapper.RecipientMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;

@RequiredArgsConstructor
@Repository
public class DialogRepository {

    private final JdbcTemplate jdbc;

    public void addPersonDialog(Integer userId, Integer dialog_id) {
        String sql = "INSERT INTO dialog (person_id, dialog_id) " +
                "VALUES (?, ?)";
        jdbc.update(sql, userId, dialog_id);
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

package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.mapper.DialogMapper;
import ru.skillbox.socnetwork.model.rsdto.DialogDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DialogRepository {
    private final JdbcTemplate jdbc;

    private List<DialogDto> getDialogs () {
        String sql = "SELECT * FROM dialog";
        return jdbc.query(sql, new DialogMapper());
    }

    private void createDialog (Integer recipient_id, Integer author_id) {
        String sql = "INSERT INTO dialog (recipient_id, author_id) VALUES (?, ?)";
        jdbc.update(sql, recipient_id, author_id);
    }
}

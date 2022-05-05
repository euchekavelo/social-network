package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.logging.DebugLogs;

@RequiredArgsConstructor
@Repository
@DebugLogs
public class NotificationRepository {
    private final JdbcTemplate jdbc;

    public void deleteAllPersonNotifications(Integer personId){
        String sql = "delete from notification where person_id = ?";
        jdbc.update(sql, personId);
    }
}

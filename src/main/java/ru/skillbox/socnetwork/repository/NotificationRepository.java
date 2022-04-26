package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.mapper.NotificationMapper;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class NotificationRepository {
    private final JdbcTemplate jdbc;


    public List<Notification> getNotifications(int offset, int perPage){
        String sql = "select * from notification";
        return jdbc.query(sql, new NotificationMapper());
    }
}

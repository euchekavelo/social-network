package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.logging.DebugLogs;

import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.mapper.NotificationMapper;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
@DebugLogs
public class NotificationRepository {
    private final JdbcTemplate jdbc;


    public List<Notification> getNotifications(int offset, int perPage) {
        String sql = "select * from notification";
        List<Notification> not = jdbc.query(sql, new NotificationMapper());
        return not;
    }

    public Notification addNotification(NotificationDto notificationDto) {
        String sql = "insert into notification (type_id, sent_time, person_id, entity_id, contact)" +
                " values (?, ?, ?, ?, ?)";

        jdbc.update(sql, notificationDto.getTypeId(), notificationDto.getSentTime(),
                notificationDto.getPersonId(), notificationDto.getEntityId(), notificationDto.getContact());
        return getLastNotification();
    }

    public Notification getLastNotification() {
        String sql = "select * from notification where id = 6";
        return jdbc.queryForObject(sql, new NotificationMapper());
    }
}

package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.logging.DebugLogs;

import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.entity.NotificationType;
import ru.skillbox.socnetwork.model.mapper.NotificationMapper;
import ru.skillbox.socnetwork.model.mapper.NotificationTypeMapper;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
@DebugLogs
public class NotificationRepository {
    private final JdbcTemplate jdbc;

    public void deleteAllPersonNotifications(Integer personId) {
        String sql = "delete from notification where person_id = ?";
        jdbc.update(sql, personId);
    }


    public List<Notification> getNotifications(int offset, int perPage) {
        String sql = "select * from notification";
        List<Notification> not = jdbc.query(sql, new NotificationMapper());
        return not;
    }

    public void addNotification(NotificationDto notificationDto) {
        String sql = "insert into notification (notification_type, sent_time, person_id, entity_id, contact)" +
                " values (CAST(? AS notification_code_type), ?, ?, ?, ?)";

        jdbc.update(sql, notificationDto.getNotificationType().toString(), notificationDto.getSentTime(),
                notificationDto.getPersonId(), notificationDto.getEntityId(), notificationDto.getContact());
        System.out.println("OK");
    }

    public List<Notification> getAllNotificationsForPerson(int personId) {

        String sql = "select * from notification n join person p on n.person_id = p.id where p.id = ?";
        return jdbc.query(sql, new NotificationMapper(), personId);
    }
}

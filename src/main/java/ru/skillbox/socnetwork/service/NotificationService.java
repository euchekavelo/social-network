package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;
import ru.skillbox.socnetwork.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationDto> getNotifications(int offset, int perPag) {

        List<NotificationDto> notificationsDto = new ArrayList<>();
        List <Notification> notifications = notificationRepository.getNotifications(offset, perPag);
        for (Notification notification : notifications) {
            notificationsDto.add(new NotificationDto(notification));
        }
        return notificationsDto;
    }
}

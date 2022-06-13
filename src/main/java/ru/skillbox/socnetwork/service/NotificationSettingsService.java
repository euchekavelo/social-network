package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.NotificationSettings;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;
import ru.skillbox.socnetwork.model.rsdto.NotificationSettingsDto;
import ru.skillbox.socnetwork.repository.NotificationSettingsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationSettingsService {
    NotificationSettingsRepository notificationSettingsRepository;

    public void changeSettingsToNotification(String notificationTypeString, String enableString) {
        Integer currentId = PostService.getSecurityUser().getId();
        TypeNotificationCode notificationType = TypeNotificationCode.valueOf(notificationTypeString);
        Boolean enable = Boolean.valueOf(enableString);
        notificationSettingsRepository.changeNotificationSettings(notificationType, enable, currentId);
    }

    public List<NotificationSettingsDto> getSettingsForUser() {

        Integer currentId = PostService.getSecurityUser().getId();
        List<NotificationSettings> notificationSettings =
                notificationSettingsRepository.getAllNotificationsSettingsForUser(currentId);
        return notificationSettingsToDto(notificationSettings);

    }

    private List<NotificationSettingsDto> notificationSettingsToDto(List<NotificationSettings> notificationSettings) {

        List<NotificationSettingsDto> notificationSettingsDto = new ArrayList<>();
        for (NotificationSettings settings : notificationSettings) {
            notificationSettingsDto.add(new NotificationSettingsDto(settings));
        }
        return notificationSettingsDto;
    }
}

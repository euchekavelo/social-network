package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;
import ru.skillbox.socnetwork.model.rsdto.NotificationDtoToView;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.repository.NotificationSettingsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationAddService notificationAddService;
    private final PersonService personService;
    private final FriendsService friendsService;
    private final NotificationSettingsRepository notificationSettingsRepository;

    private final SecurityPerson securityPerson = new SecurityPerson();

    public void addNotificationForFriends(NotificationDto notificationDto) {

        List<PersonDto> friends = friendsService.getUserFriends();
        for (PersonDto friend : friends) {
            addNotificationForOnePerson(notificationDto, friend.getId());
        }
    }

    public void addNotificationForOnePerson(NotificationDto notificationDto, Integer destinationId) {

        boolean settingsEnable = notificationSettingsRepository.checkSettingsForNotification
                (notificationDto.getNotificationType(),destinationId);
        if (settingsEnable){
            notificationDto.setDistUserId(destinationId);
            notificationAddService.addNotificationForOnePerson(notificationDto);
        }

    }

    public List<NotificationDtoToView> getAllNotifications() {

        personService.addOnlinePerson(securityPerson.getPersonId());

        List<Notification> notifications = notificationAddService.getAllNotifications(securityPerson.getPersonId());
        return notificationsToDto(notifications);
    }

    private List<NotificationDtoToView> notificationsToDto(List<Notification> notifications) {
        List<NotificationDto> notificationsDto = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationsDto.add(new NotificationDto(notification));
        }

        return notificationDtoToNotificationViews(notificationsDto);
    }

    private List<NotificationDtoToView> notificationDtoToNotificationViews(List<NotificationDto> notificationsDto) {

        List<NotificationDtoToView> notificationDtoToView = new ArrayList<>();
        for (NotificationDto notification : notificationsDto) {
            notificationDtoToView.add(new NotificationDtoToView(notification,
                    personService.getById(notification.getPersonId()),
                    notification.getTitle()
            ));
        }
        return notificationDtoToView;
    }

    public void readAllNotifications(int id, boolean all) {
        notificationAddService.readAllNotifications(id, all);
    }
}

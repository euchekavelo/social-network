package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;
import ru.skillbox.socnetwork.model.rsdto.NotificationDtoToView;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.repository.NotificationRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PersonService personService;
    private final FriendsService friendsService;

    public List<NotificationDto> getNotifications(int offset, int perPage) {

        List<NotificationDto> notificationsDto = new ArrayList<>();
        List<Notification> notifications = notificationRepository.getNotifications(offset, perPage);
        for (Notification notification : notifications) {
            notificationsDto.add(new NotificationDto(notification));
        }
        return notificationsDto;
    }

    public List<NotificationDtoToView> getAllNotificationsForFriends() {

        /*
        Alexander Luzyanin add person to onlinePersonMap<Integer, Long>
         */
        personService.addOnlinePerson(getPersonId());

        List<PersonDto> friends = friendsService.getUserFriends();
        List<Notification> notifications = new ArrayList<>();

        for (PersonDto friend : friends) {
            List<Notification> friendNotifications =
                    notificationRepository.getAllNotificationsForPerson(friend.getId());
            notifications.addAll(friendNotifications);
        }
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
                    "Очередной бесполезный пост"
            ));
        }
        return notificationDtoToView;
    }

    public Integer getPersonId() {
        SecurityUser auth = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return auth.getId();
    }
}

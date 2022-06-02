package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.entity.NotificationType;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;
import ru.skillbox.socnetwork.model.entity.enums.TypeReadStatus;
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

    public void addNotification(Integer currentUserId, Integer postId,
                                long currentTime, TypeNotificationCode notificationType, String title) {

        List<PersonDto> friends = friendsService.getUserFriends();
        for (PersonDto friend : friends) {
            NotificationDto notificationDto = new NotificationDto(notificationType, currentTime,
                    currentUserId, postId, friend.getId(), TypeReadStatus.SENT, title);
            notificationRepository.addNotification(notificationDto);
        }
    }

    public List<NotificationDtoToView> getAllNotifications() {

        int currentId = PostService.getSecurityUser().getId();
        List<Notification> notifications = notificationRepository.getAllNotificationsForPerson(currentId);
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

        if (all) {
            notificationRepository.readAllNotificationByUser(PostService.getSecurityUser().getId());
        } else {
            notificationRepository.readUsersNotificationById(id);
        }
    }
}

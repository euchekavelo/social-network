package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;
import ru.skillbox.socnetwork.model.entity.enums.TypeReadStatus;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;
import ru.skillbox.socnetwork.model.rsdto.NotificationDtoToView;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.repository.NotificationRepository;
import ru.skillbox.socnetwork.repository.NotificationSettingsRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
@EnableScheduling
public class NotificationService {

    private final NotificationAddService notificationAddService;
    private final PersonService personService;
    private final FriendsService friendsService;
    private final NotificationSettingsRepository notificationSettingsRepository;

    public void addNotificationForFriends(NotificationDto notificationDto) {

        List<PersonDto> friends = friendsService.getUserFriends();
        for (PersonDto friend : friends) {
            addNotificationForOnePerson(notificationDto, friend.getId());
        }
    }

    public void addNotificationForOnePerson(NotificationDto notificationDto,
                                            Integer destinationId) {

        boolean settingsEnable = notificationSettingsRepository.checkSettingsForNotification
                (notificationDto.getNotificationType(), destinationId);
        if (settingsEnable) {
            notificationDto.setDistUserId(destinationId);
            notificationAddService.addNotificationForOnePerson(notificationDto);
        }

    }

    public List<NotificationDtoToView> getAllNotifications() {

        Integer currentId = PostService.getSecurityUser().getId();
        List<Notification> notifications = notificationAddService.getAllNotifications(currentId);
        //notificationRepository.getAllNotificationsForPerson(currentId);
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

    public Integer getPersonId() {
        SecurityUser auth = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return auth.getId();
    }

@Scheduled
    public void checkIfBirthdayOfFriends() {

        List<PersonDto> friends = friendsService.getUserFriends();
        for (PersonDto friend : friends) {
            int friendId = friend.getId();
            if (checkIfBirthDayToday(friendId)) {
                createNotificationAboutBirthdayFriend(friendId);
            }
        }
    }

    private boolean checkIfBirthDayToday(Integer personId) {

        long birthDayDate = personService.getPersonBirthDay(personId).getBirthDate();
        LocalDate birthDayDay = Instant.ofEpochMilli(birthDayDate).atZone(ZoneId.systemDefault())
                .toLocalDate();
        int dayOfBirthday = birthDayDay.getDayOfMonth();
        String monthOfBirthday = birthDayDay.getMonth().toString();


        LocalDate today = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault())
                .toLocalDate();
        int dayOfYear = today.getDayOfMonth();
        String month = today.getMonth().toString();

        return (dayOfBirthday == dayOfYear) && ( month.equals(monthOfBirthday));
    }

    private void createNotificationAboutBirthdayFriend(Integer personId) {
        Integer currentId = PostService.getSecurityUser().getId();
        NotificationDto notificationDto = new NotificationDto(
                TypeNotificationCode.FRIEND_BIRTHDAY, System.currentTimeMillis(), personId,
                "У вашего друга сегодня день рождения!");
        addNotificationForOnePerson(notificationDto, currentId);
    }
}

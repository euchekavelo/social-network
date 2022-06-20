package ru.skillbox.socnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@AllArgsConstructor
public class SchedulerService {
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 10000)
    public void checkIfBirthdayOfFriends(){
        notificationService.checkIfBirthdayOfFriends();
    }


}

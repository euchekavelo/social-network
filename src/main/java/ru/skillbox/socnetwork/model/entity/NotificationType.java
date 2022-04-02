package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;

@Data
public class NotificationType {
    private Integer id;
    private TypeNotificationCode code;
    private String name;
}

package ru.skillbox.socnetwork.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;
import ru.skillbox.socnetwork.model.entity.enums.TypeReadStatus;

@Data
public class Notification {
    private Integer id;
    @JsonProperty("notification_type")
    private TypeNotificationCode notificationType;
    @JsonProperty("sent_time")
    private Long sentTime;
    @JsonProperty("person_id")
    private Integer personId;
    @JsonProperty("entity_id")
    private Integer entityId;
    private Integer distUserId;
    private TypeReadStatus status;
    private String title;
}

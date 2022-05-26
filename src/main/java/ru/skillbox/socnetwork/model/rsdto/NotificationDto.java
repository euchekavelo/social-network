package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.Notification;
import ru.skillbox.socnetwork.model.entity.NotificationType;
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDto {
    private Integer id;
    //    @JsonProperty("type_id")
//    private Integer typeId;
    @JsonProperty("notification_type")
    private TypeNotificationCode notificationType;
    @JsonProperty("sent_time")
    private Long sentTime;
    @JsonProperty("person_id")
    private Integer personId;
    @JsonProperty("entity_id")
    private Integer entityId;
    private String contact;

    public NotificationDto(TypeNotificationCode notificationType, Long sentTime,
                           Integer personId, Integer entityId, String contact) {
        this.notificationType = notificationType;
        this.sentTime = sentTime;
        this.personId = personId;
        this.entityId = entityId;
        this.contact = contact;
    }

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.notificationType = notification.getNotificationType();
        this.sentTime = notification.getSentTime();
        this.personId = notification.getPersonId();
        this.entityId = notification.getEntityId();
        this.contact = notification.getContact();
    }
}



package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.Notification;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDto {
    private Integer id;
    @JsonProperty("type_id")
    private Integer typeId;
    @JsonProperty("sent_time")
    private Long sentTime;
    @JsonProperty("person_id")
    private Integer personId;
    @JsonProperty("entity_id")
    private Integer notificationTypeId;
    private String contact;

    public NotificationDto(Notification notification){
        this.id = notification.getId();
        this.typeId = notification.getNotificationTypeId();
        this.sentTime = notification.getSentTime();
        this.personId = notification.getPersonId();
        this.notificationTypeId = notification.getNotificationTypeId();
        this.contact = notification.getContact();
    }
}



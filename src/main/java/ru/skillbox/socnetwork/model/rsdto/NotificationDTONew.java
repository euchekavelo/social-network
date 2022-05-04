package ru.skillbox.socnetwork.model.rsdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.PersonTemp;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTONew {
    private Integer id;
    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("sent_time")
    private Long sentTime;
    @JsonProperty("entity_author")
    private PersonTemp entityAuthor;
    private String info;

//    public NotificationDTONew(Integer id,String typeId, Long sentTime,
//                               String entityAuthor, String contact) {
//        this.id = id;
//        this.eventType = typeId;
//        this.sentTime = sentTime;
//        this.entityAuthor = entityAuthor;
//        this.info = contact;
//    }


}

package ru.skillbox.socnetwork.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Notification {
    private Integer id;
    @JsonProperty("type_id")
    private Integer typeId;
    @JsonProperty("sent_time")
    private Long sentTime;
    @JsonProperty("person_id")
    private Integer personId;
    @JsonProperty("entity_id")
    private Integer entityId;
    private String contact;
}

package ru.skillbox.socnetwork.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Оповещение")
public class Notification {
    private Integer id;
    @JsonProperty("type_id")
    @Schema(description = "Идентификатор типа", example = "3")
    private Integer typeId;
    @JsonProperty("sent_time")
    @Schema(example = "1630627200000")
    private Long sentTime;
    @JsonProperty("person_id")
    @Schema(description = "Идентификатор пользователя", example = "3")
    private Integer personId;
    @JsonProperty("entity_id")
    @Schema(description = "Идентификатор сущности, относительно которой отправлено оповещение (комментарий, друг, пост или сообщение)", example = "3")
    private Integer entityId;
    private String contact;
}

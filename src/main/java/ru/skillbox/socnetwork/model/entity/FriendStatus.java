package ru.skillbox.socnetwork.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.enums.TypeCode;

import java.time.LocalDateTime;

@Data
public class FriendStatus {
    @Schema(description = "Статус дружбы")
    private Integer id;
    @Schema(example = "1630627200000")
    private Long time;
    private String name;
    private TypeCode code;
}

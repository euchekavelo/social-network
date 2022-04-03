package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PutLikeDto {
    @JsonProperty("item_id")
    Integer itemId;
    String type;
}
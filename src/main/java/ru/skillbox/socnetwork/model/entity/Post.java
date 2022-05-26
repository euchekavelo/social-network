package ru.skillbox.socnetwork.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Пост")
public class Post {
    private Integer id;
    @Schema(example = "1630627200000")
    private Long time;
    @Schema(description = "Идентификатор автора поста", example = "13")
    private Integer author;
    @Schema(example = "Самый лучший пост")
    private String title;
    @JsonProperty("post_text")
    @Schema(example = "Самый лучший текст самого лучшего поста")
    private String postText;
    @JsonProperty("is_blocked")
    private Boolean isBlocked = false;
    @Schema(example = "666")
    private Integer likes;
}

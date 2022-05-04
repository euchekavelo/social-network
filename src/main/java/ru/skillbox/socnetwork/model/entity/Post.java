package ru.skillbox.socnetwork.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Post {
    private Integer id;
    private Long time;
    private Integer author;
    private String title;
    @JsonProperty("post_text")
    private String postText;//
    @JsonProperty("is_blocked")
    private Boolean isBlocked = false;//
    private Integer likes;//
}

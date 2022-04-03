package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PostLike {
    private Integer id;
    private Long time;
    private Integer personId;
    private Integer postId;

    public PostLike(Integer personId, Integer postId) {
        this.time = System.currentTimeMillis();
        this.personId = personId;
        this.postId = postId;
    }
}

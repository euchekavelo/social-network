package ru.skillbox.socnetwork.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class CommentLike {
    private Integer id;
    private Long time;
    private Integer personId;
    private Integer commentId;

    public CommentLike(Integer personId, Integer commentId) {
        this.time = System.currentTimeMillis();
        this.personId = personId;
        this.commentId = commentId;
    }
}

package ru.skillbox.socnetwork.model.rsdto.postdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.skillbox.socnetwork.model.entity.PostComment;

import java.time.ZoneOffset;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    Integer id;
    @JsonProperty("parent_id")
    Integer parentId;
    @JsonProperty("post_id")
    Integer postId;
    @JsonProperty("comment_text")
    String commentText;
    Long time;
    @JsonProperty("author_id")
    Integer authorId;
    @JsonProperty("is_blocked")
    Boolean isBlocked;

    public CommentDto(PostComment comment) {
        this.id = comment.getId();
        this.parentId = comment.getParentId();
        this.postId = comment.getPostId();
        this.commentText = comment.getCommentText();
        this.time = comment.getTime();
        this.authorId = comment.getAuthorId();
        this.isBlocked = comment.getIsBlocked();
    }
}

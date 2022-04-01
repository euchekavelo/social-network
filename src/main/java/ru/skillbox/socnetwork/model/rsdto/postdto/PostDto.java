package ru.skillbox.socnetwork.model.rsdto.postdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.rsdto.PersonResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Data
public class PostDto {
    Integer id;
    Long time;
    PersonResponse author;
    String title;
    @JsonProperty("post_text")
    String postText;
    Integer likes;
    @JsonProperty("is_blocked")
    Boolean isBlocked;
    List<CommentDto> comments;
    String type;

    public PostDto(Post post, PersonResponse personResponse, List<CommentDto> comments) {
        this.id = post.getId();
        this.time = post.getTime();
        this.author = personResponse;
        this.title = post.getTitle();
        this.postText = post.getPostText();
        this.likes = post.getLikes();
        this.isBlocked = post.isBlocked();
        this.comments = comments;
        this.type = "POSTED";
    }

    private long getLong(LocalDateTime time) {
        return time.toEpochSecond(ZoneOffset.of("+00:00"));
    }
}

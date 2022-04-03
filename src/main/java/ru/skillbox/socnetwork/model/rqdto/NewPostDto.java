package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.skillbox.socnetwork.model.entity.Tag;

import java.util.List;

@Data
@RequiredArgsConstructor
public class NewPostDto {
    String title;
    @JsonProperty("post_text")
    String postText;
    Integer authorId;
    Long time;
    List<Tag> tags;
}
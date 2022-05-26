package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Schema(hidden = true)
public class NewPostDto {
    String title;
    @JsonProperty("post_text")
    String postText;
    Integer authorId;
    Long time;
    List<String> tags;
}
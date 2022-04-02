package ru.skillbox.socnetwork.model.rqdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewPostDto {
    String title;
    String postText;
    Integer authorId;
    Long time;

    public NewPostDto(String title, String postText, int authorId) {
        this.title = title;
        this.postText = postText;
        this.authorId = authorId;
        this.time = System.currentTimeMillis();
    }

    public NewPostDto(String title, String postText, long publishDate, int authorId) {
        this.title = title;
        this.postText = postText;
        this.authorId = authorId;
        this.time = publishDate;
    }
}
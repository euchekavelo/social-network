package ru.skillbox.socnetwork.model.rsdto.postdto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikedDto {
    Boolean likes;

    public LikedDto(boolean isLiked) {
        this.likes = isLiked;
    }
}

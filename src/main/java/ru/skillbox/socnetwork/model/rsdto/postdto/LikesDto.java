package ru.skillbox.socnetwork.model.rsdto.postdto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LikesDto {
    Integer likes;
    List<Integer> users;
}

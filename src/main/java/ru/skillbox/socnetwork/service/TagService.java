package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.rsdto.postdto.TagDTO;
import ru.skillbox.socnetwork.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public List<TagDTO> getTags(String tag) {
        return List.of(
                new TagDTO(1, "java"),
                new TagDTO(2, "code"),
                new TagDTO(3, "spring"),
                new TagDTO(4, "boot"),
                new TagDTO(5, "enum"));
    }
}

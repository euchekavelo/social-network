package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Tag;
import ru.skillbox.socnetwork.model.rqdto.NewPostDto;
import ru.skillbox.socnetwork.repository.Post2TagRepository;
import ru.skillbox.socnetwork.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final Post2TagRepository post2TagRepository;


    public List<String> getPostTags(int postId) {
        return tagRepository
                .getPostTags(postId)
                .stream()
                .map(Tag::getTag)
                .collect(Collectors.toList());
    }

    public void addTags(String tag) {
        try {
            tagRepository.addTag(tag);
        } catch (DuplicateKeyException ignored) {
        }
    }

    public void addTagsFromNewPost(int postId, NewPostDto newPostDto) {
        List<String> postTags =  newPostDto.getTags();
        postTags.forEach(this::addTags);
        List<Tag> tags = tagRepository.getAllTags();
        postTags.forEach(tag -> post2TagRepository.addTag2Post(postId, getTagId(tags, tag)));
    }

    private int getTagId(List<Tag> tags, String tag) {
        return tags.stream().filter(t -> t.getTag().equals(tag)).findFirst().orElseThrow().getId();
    }

    private String getTagName(List<Tag> tags, int tagId) {
        return tags.stream().filter(tag -> tag.getId().equals(tagId)).findFirst().orElseThrow().getTag();
    }

    public void deletePostTags(int postId) {
        post2TagRepository.deletePostTags(postId);
    }
}

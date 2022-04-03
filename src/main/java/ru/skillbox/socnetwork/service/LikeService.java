package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.PostLike;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikedDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikesDto;
import ru.skillbox.socnetwork.repository.PostLikeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    public LikesDto getLikes(Integer itemId) {
        LikesDto likesDto = new LikesDto();
        List<PostLike> postLikeList = postLikeRepository.getPostLikes(itemId);
        likesDto.setLikes(postLikeList.size());
        likesDto.setUsers(postLikeList.stream().map(PostLike::getPersonId).collect(Collectors.toList()));
        return likesDto;
    }

    public LikesDto putAndGetAllPostLikes(Integer personId, Integer itemId) {
        Optional<PostLike> optionalLikeDto = Optional.ofNullable(postLikeRepository.getPersonLike(personId, itemId));
        if (optionalLikeDto.isEmpty()){
            postLikeRepository.addLike(personId, itemId);
            LikesDto likesDto = getLikes(itemId);
            postService.updatePostLikeCount(likesDto.getLikes(), itemId);
            return likesDto;
        }
        return getLikes(itemId);
    }

    public LikedDto getLiked(Integer personId, Integer itemId) {
        return new LikedDto(postLikeRepository.getIsLiked(personId, itemId));
    }

    public LikesDto deletePostLike(Integer id, int itemId) {
        LikesDto likesDto = new LikesDto();
        postLikeRepository.deleteLike(id, itemId);
        List<PostLike> postLikeList = postLikeRepository.getPostLikes(itemId);
        likesDto.setLikes(postLikeList.size());
        return likesDto;
    }
}

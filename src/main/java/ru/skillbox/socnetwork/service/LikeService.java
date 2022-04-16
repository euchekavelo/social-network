package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.CommentLike;
import ru.skillbox.socnetwork.model.entity.PostLike;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikedDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikesDto;
import ru.skillbox.socnetwork.repository.CommentLikeRepository;
import ru.skillbox.socnetwork.repository.PostLikeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostService postService;

    public LikesDto getPostLikes(Integer itemId) {
        LikesDto likesDto = new LikesDto();
        List<PostLike> postLikeList = postLikeRepository.getPostLikes(itemId);
        likesDto.setLikes(postLikeList.size());
        likesDto.setUsers(postLikeList.stream().map(PostLike::getPersonId).collect(Collectors.toList()));
        return likesDto;
    }

    public LikesDto getCommentLikes(Integer itemId) {
        LikesDto likesDto = new LikesDto();
        List<CommentLike> LikeList = commentLikeRepository.getLikes(itemId);
        likesDto.setLikes(LikeList.size());
        likesDto.setUsers(LikeList.stream().map(CommentLike::getPersonId).collect(Collectors.toList()));
        return likesDto;
    }

    public LikesDto putAndGetAllPostLikes(Integer personId, Integer itemId) {
        Optional<PostLike> optionalLikeDto = Optional.ofNullable(postLikeRepository.getPersonLike(personId, itemId));
        if (optionalLikeDto.isEmpty()){
            postLikeRepository.addLike(personId, itemId);
            LikesDto likesDto = getPostLikes(itemId);
            postService.updatePostLikeCount(likesDto.getLikes(), itemId);
            return likesDto;
        }
        return getPostLikes(itemId);
    }

    public LikedDto getPostLiked(Integer personId, Integer itemId) {
        return new LikedDto(postLikeRepository.getIsLiked(personId, itemId));
    }

    public boolean isPostLiked(Integer personId, Integer itemId) {
        return postLikeRepository.getIsLiked(personId, itemId);
    }

    public boolean isCommentLiked(Integer personId, Integer itemId) {
        return commentLikeRepository.getIsLiked(personId, itemId);
    }

    public LikesDto deletePostLike(Integer id, int itemId) {
        LikesDto likesDto = new LikesDto();
        postLikeRepository.deleteLike(id, itemId);
        List<PostLike> postLikeList = postLikeRepository.getPostLikes(itemId);
        likesDto.setLikes(postLikeList.size());
        return likesDto;
    }
}

package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.exception.InvalidRequestException;
import ru.skillbox.socnetwork.model.entity.CommentLike;
import ru.skillbox.socnetwork.model.entity.PostLike;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikedDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.LikesDto;
import ru.skillbox.socnetwork.repository.CommentLikeRepository;
import ru.skillbox.socnetwork.repository.PostLikeRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@DebugLogs
public class LikeService {

    private static final String POST = "Post";
    private static final String COMMENT = "Comment";

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostService postService;

    public LikesDto getLikes(Integer itemId, String type) throws InvalidRequestException {
        LikesDto likesDto = new LikesDto();
        if (type.equals(POST)) {
            List<PostLike> postLikeList = postLikeRepository.getPostLikes(itemId);
            likesDto.setLikes(postLikeList.size());
            likesDto.setUsers(postLikeList.stream().map(PostLike::getPersonId).collect(Collectors.toList()));
            return likesDto;
        } else if (type.equals(COMMENT)) {
            List<CommentLike> likeList = commentLikeRepository.getLikes(itemId);
            likesDto.setLikes(likeList.size());
            likesDto.setUsers(likeList.stream().map(CommentLike::getPersonId).collect(Collectors.toList()));
            return likesDto;
        }
        throw new InvalidRequestException("Bad like type. Required 'Post' or 'Comment' types");
    }

    public LikesDto putAndGetAllLikes(Integer itemId, String type) throws InvalidRequestException {
        if (type.equals(POST)) {
            Optional<PostLike> optionalLikeDto = Optional
                    .ofNullable(postLikeRepository.getPersonLike(getPersonId(), itemId));
            if (optionalLikeDto.isEmpty()) {
                postLikeRepository.addLike(getPersonId(), itemId);
                LikesDto likesDto = getLikes(itemId, POST);
                postService.updatePostLikeCount(likesDto.getLikes(), itemId);
                return likesDto;
            }
            return getLikes(itemId, POST);
        } else if (type.equals(COMMENT)) {
            Optional<CommentLike> optionalLikeDto = Optional
                    .ofNullable(commentLikeRepository.getPersonLike(getPersonId(), itemId));
            if (optionalLikeDto.isEmpty()) {
                commentLikeRepository.addLike(getPersonId(), itemId);
                LikesDto likesDto = getLikes(itemId, COMMENT);
                postService.updateCommentLikeCount(likesDto.getLikes(), itemId);
            }
            return getLikes(itemId, COMMENT);
        }
        throw new InvalidRequestException("Bad like type to get likes. Required 'Post' or 'Comment' types");

    }

    public LikedDto getLiked(Integer itemId, String type) throws InvalidRequestException {
        int personId = getPersonId();
        if (type.equals(POST)) {
            return new LikedDto(postLikeRepository.getIsLiked(personId, itemId));
        } else if (type.equals(COMMENT)) {
            return new LikedDto(isCommentLiked(personId, itemId));
        }
        throw new InvalidRequestException("Bad like type for get liked. Required 'Post' or 'Comment' types");
    }

    public boolean isPostLiked(Integer personId, Integer itemId) {
        return postLikeRepository.getIsLiked(personId, itemId);
    }

    public boolean isCommentLiked(Integer personId, Integer itemId) {
        return commentLikeRepository.getIsLiked(personId, itemId);
    }

    public LikesDto deletePostLike(int itemId, String type) throws InvalidRequestException {
        LikesDto likesDto = new LikesDto();
        if (type.equals(POST)) {
            postLikeRepository.deleteLike(getPersonId(), itemId);
            List<PostLike> likeList = postLikeRepository.getPostLikes(itemId);
            likesDto.setLikes(likeList.size());
            postService.updatePostLikeCount(likesDto.getLikes(), itemId);
            return likesDto;
        } else if (type.equals(COMMENT)) {
            commentLikeRepository.deleteLike(getPersonId(), itemId);
            List<CommentLike> likeList = commentLikeRepository.getLikes(itemId);
            likesDto.setLikes(likeList.size());
            postService.updateCommentLikeCount(likesDto.getLikes(), itemId);
            return likesDto;
        }
        throw new InvalidRequestException("Bad like type for get liked. Required 'Post' or 'Comment' types");
    }

    private SecurityUser getSecurityUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) auth.getPrincipal();
    }

    private int getPersonId() {
        return getSecurityUser().getId();
    }
}

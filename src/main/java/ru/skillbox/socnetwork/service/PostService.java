package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.exception.ExceptionText;
import ru.skillbox.socnetwork.exception.InvalidRequestException;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.entity.PostComment;
import ru.skillbox.socnetwork.model.rqdto.NewPostDto;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.CommentDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.repository.NotificationRepository;
import ru.skillbox.socnetwork.repository.PostCommentRepository;
import ru.skillbox.socnetwork.repository.PostLikeRepository;
import ru.skillbox.socnetwork.repository.PostRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@DebugLogs
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository commentRepository;
    private final PersonService personService;
    private final PostLikeRepository likeRepository;
    private final NotificationRepository notificationRepository;
    private final TagService tagService;

    public List<PostDto> getAll(int offset, int perPage) {
        return getPostDtoListOfAllPersons(postRepository.getAlreadyPostedWithOffset(offset, perPage), getPersonId());
    }

    public List<PostDto> getWall(int personId, int offset, int perPage) {
        List<Post> posts = postRepository.getByAuthorIdWithOffset(personId, offset, perPage);
        PersonDto personDto = new PersonDto(personService.getById(personId));
        return getPostDtoListOfOnePerson(posts, personDto);
    }

    public PostDto getById(int postId) throws InvalidRequestException {
        try {
            Post post = postRepository.getById(postId);
            PersonDto personDto = new PersonDto(personService.getById(post.getAuthor()));
            List<CommentDto> commentDtoList = getCommentDtoList(postId);
            List<String> tags = tagService.getPostTags(postId);
            return new PostDto(post, personDto, commentDtoList, tags, likeRepository.getIsLiked(getPersonId(), postId));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidRequestException(ExceptionText.POST_INCORRECT_CANT_FIND_ID.getMessage() + postId);
        }
    }

    public void deletePostById(int postId) throws InvalidRequestException {
        if (postRepository.deleteById(postId) == 0) {
            throw new InvalidRequestException(ExceptionText.POST_ID_NOT_FOUND_TO_DELETE.getMessage());
        } else {
            commentRepository.deleteCommentsByPostId(postId);
            tagService.deletePostTags(postId);
        }
    }

    public List<CommentDto> getCommentDtoList(int postId) {
        List<PostComment> postComments = commentRepository.getCommentsByPostId(getPersonId(), postId);
        if (postComments == null) {
            return new ArrayList<>();
        }
        return postComments.stream()
                .map(comment -> new CommentDto(comment, new PersonDto(personService.getById(comment.getAuthorId()))))
                .collect(Collectors.toList());
    }

    private List<PostDto> getPostDtoListOfOnePerson(List<Post> posts, PersonDto personDto) {
        return posts.stream().map(post -> {
                    PostDto postDto = new PostDto(
                            post,
                            personDto,
                            getCommentDtoList(post.getId()),
                            tagService.getPostTags(post.getId()));
                    postDto.setIsLiked(likeRepository.getIsLiked(personDto.getId(), post.getId()));
                    return postDto;
                }
        ).collect(Collectors.toList());
    }


    private List<PostDto> getPostDtoListOfAllPersons(List<Post> posts, Integer personId) {
        return posts.stream().map(post -> {
                    PostDto postDto = new PostDto(
                            post,
                            new PersonDto(personService.getById(post.getAuthor())),
                            getCommentDtoList(post.getId()),
                            tagService.getPostTags(post.getId()));
                    postDto.setIsLiked(likeRepository.getIsLiked(personId, post.getId()));
                    return postDto;
                }
        ).collect(Collectors.toList());
    }

    public PostDto addPost(NewPostDto newPostDto, long publishDate) throws InvalidRequestException {
        if (publishDate == -1) {
            newPostDto.setTime(System.currentTimeMillis());
        } else {
            newPostDto.setTime(publishDate);
        }
        Post post = postRepository.addPost(newPostDto);

        NotificationDto notificationDto = new NotificationDto(1, System.currentTimeMillis(),
                10, post.getId(), "e-mail");
        notificationRepository.addNotification(notificationDto);

        tagService.addTagsFromNewPost(post.getId(), newPostDto);

        return new PostDto(post, new PersonDto(
                personService.getById(newPostDto.getAuthorId())),
                new ArrayList<>(),
                tagService.getPostTags(post.getId()));

    }

    public PostDto editPost(int postId, NewPostDto newPostDto) throws InvalidRequestException {
        if (getPersonId().equals(newPostDto.getAuthorId())) {
            throw new InvalidRequestException(ExceptionText.POST_INCORRECT_AUTHOR_TO_EDIT.getMessage());
        }
        tagService.editOldTags(postId, newPostDto);
        postRepository.editPost(postId, newPostDto);
        return getById(postId);
    }

    public void updatePostLikeCount(Integer likes, Integer postId) {
        postRepository.updateLikeCount(likes, postId);
    }

    public void updateCommentLikeCount(Integer likes, Integer postId) {
        commentRepository.updateLikeCount(likes, postId);
    }

    public CommentDto addCommentToPost(CommentDto comment, int id) {
        comment.setAuthor(new PersonDto(personService.getById(getPersonId())));
        comment.setTime(System.currentTimeMillis());
        comment.setPostId(id);
        comment.setIsBlocked(false);
        commentRepository.add(comment);
        return comment;
    }

    public CommentDto editCommentToPost(CommentDto comment) {
        commentRepository.edit(comment);
        return comment;
    }

    public CommentDto deleteCommentToPost(int commentId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentId);
        commentRepository.deleteById(commentId);
        return commentDto;
    }

    public List<PostDto> choosePostsWhichContainsText(String text, long dateFrom, long dateTo, String author,
                                                      int perPage, int currentPersonId) {

        String[] authorNameSurname = author.split("\\s", 2);
        String authorName = authorNameSurname[0];
        String authorSurname = authorNameSurname.length >= 2 ? authorNameSurname[1] : "";

        List<Post> posts = postRepository.choosePostsWhichContainsText(text, dateFrom, dateTo,
                authorName, authorSurname, perPage);
        return getPostDtoListOfAllPersons(posts, currentPersonId);
    }

    public Integer getPostCount() {
        return postRepository.getPostCount();
    }

    private Integer getPersonId() {
        SecurityUser auth = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return auth.getId();
    }
}

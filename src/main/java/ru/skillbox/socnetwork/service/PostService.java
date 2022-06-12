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
import ru.skillbox.socnetwork.model.entity.enums.TypeNotificationCode;
import ru.skillbox.socnetwork.model.rsdto.NotificationDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.CommentDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.NewPostDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.repository.NotificationRepository;
import ru.skillbox.socnetwork.repository.PostCommentRepository;
import ru.skillbox.socnetwork.repository.PostLikeRepository;
import ru.skillbox.socnetwork.repository.PostRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@DebugLogs
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository commentRepository;
    private final PostLikeRepository likeRepository;
    private final NotificationRepository notificationRepository;

    private final PersonService personService;
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

    private PostComment getPostCommentById(int commentId) throws InvalidRequestException {
        try {
            return commentRepository.getById(commentId);
        } catch (EmptyResultDataAccessException exception) {
            throw new InvalidRequestException(ExceptionText.COMMENT_INCORRECT_CANT_FIND_ID.getMessage() + commentId);
        }
    }

    public void deletePostById(int postId) throws InvalidRequestException {
        PostDto oldPost = this.getById(postId);
        Integer authorId = oldPost.getAuthor().getId();
        if (!getPersonId().equals(authorId)) {
            throw new InvalidRequestException(ExceptionText.POST_INCORRECT_AUTHOR_TO_DELETE.getMessage() + authorId);
        } else {
            postRepository.deleteById(postId);
            commentRepository.deleteCommentsByPostId(postId);
            tagService.deletePostTags(postId);
        }
    }

    public List<CommentDto> getCommentDtoList(int postId) {
        List<PostComment> postComments = commentRepository.getLikedParentCommentsByPostId(getPersonId(), postId);
        if (postComments.isEmpty()) {
            return new ArrayList<>();
        }
        List<PostComment> postSubComments = commentRepository.getLikedSubCommentsByPostId(getPersonId(), postId);

        return postComments.stream()
                .map(comment -> getCommentDtoWithSubComments(postSubComments, comment))
                .collect(Collectors.toList());
    }

    private CommentDto getCommentDtoWithSubComments(List<PostComment> postSubComments, PostComment comment) {
        CommentDto commentDto = new CommentDto(comment, new PersonDto(personService.getById(comment.getAuthorId())));
        if (!postSubComments.isEmpty()) {
            List<CommentDto> subComments = postSubComments.stream()
                    .filter(c -> Objects.equals(c.getParentId(), comment.getId()))
                    .map(c -> new CommentDto(c, new PersonDto(personService.getById(c.getAuthorId()))))
                    .collect(Collectors.toList());
            commentDto.setSubComments(subComments);
            System.out.println("\n!!!!!!!!!!!!!!!!" + commentDto + "!!!!!!!!!!!!!!!!\n");
        } else {
            commentDto.setSubComments(new ArrayList<>());
        }
        return commentDto;
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
        long currentTime;
        if (publishDate == -1) {
            currentTime = System.currentTimeMillis();
        } else {
            currentTime = publishDate;
        }
        newPostDto.setTime(currentTime);
        Post post = postRepository.addPost(newPostDto);

        NotificationDto notificationDto = new NotificationDto(TypeNotificationCode.POST, currentTime,
                newPostDto.getAuthorId(), post.getId(), "e-mail");

        notificationRepository.addNotification(notificationDto);

        tagService.addTagsFromNewPost(post.getId(), newPostDto);

        return new PostDto(post, new PersonDto(
                personService.getById(newPostDto.getAuthorId())),
                new ArrayList<>(),
                tagService.getPostTags(post.getId()));

    }

    public PostDto editPost(int postId, NewPostDto newPostDto) throws InvalidRequestException {
        PostDto oldPost = this.getById(postId);
        Integer authorId = oldPost.getAuthor().getId();
        if (!getPersonId().equals(authorId)) {
            throw new InvalidRequestException(ExceptionText.POST_INCORRECT_AUTHOR_TO_EDIT.getMessage() + authorId);
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

    public CommentDto editCommentToPost(CommentDto comment) throws InvalidRequestException {
        Integer authorId = this.getPostCommentById(comment.getId()).getAuthorId();
        if (!getPersonId().equals(authorId)) {
            throw new InvalidRequestException(ExceptionText.COMMENT_INCORRECT_AUTHOR_TO_EDIT.getMessage() + authorId);
        }
        commentRepository.edit(comment);
        return comment;
    }

    public CommentDto deleteCommentToPost(int commentId) throws InvalidRequestException {
        Integer authorId = this.getPostCommentById(commentId).getAuthorId();
        if (!getPersonId().equals(authorId)) {
            throw new InvalidRequestException(ExceptionText.COMMENT_INCORRECT_AUTHOR_TO_DELETE.getMessage() + authorId);
        }
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentId);
        commentRepository.deleteById(commentId);
        return commentDto;
    }

    public List<PostDto> choosePostsWhichContainsText(String text, long dateFrom, long dateTo, String author,
                                                      List<String> tags, int perPage, int currentPersonId) {

        String[] authorNameSurname = author.split("\\s", 2);
        String authorName = authorNameSurname[0];
        String authorSurname = authorNameSurname.length >= 2 ? authorNameSurname[1] : "";

        List<Post> posts = postRepository.choosePostsWhichContainsTextWithTags(text, dateFrom, dateTo,
                authorName, authorSurname, getSqlString(tags), perPage);

        return getPostDtoListOfAllPersons(posts, currentPersonId);
    }

    public Integer getPostCount() {
        return postRepository.getPostCount();
    }

    public Integer getPersonId() {
        SecurityUser auth = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return auth.getId();
    }

    private String getSqlString(List<String> tags) {
        StringBuilder tagsString = new StringBuilder();
        if (tags.size() > 0) {
            tagsString.append(" and (");
            for (int i = 0; i < tags.size(); i++) {
                tagsString.append("t.tag like '").append(tags.get(i)).append("'");
                if (i != tags.size() - 1) {
                    tagsString.append(" OR ");
                } else {
                    tagsString.append(")");
                }
            }
            tagsString.append(" group by p.id order by count(t.tag) desc");
        } else {
            tagsString.append(" group by p.id");
        }
        return tagsString.toString();
    }

}

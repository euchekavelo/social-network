package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.entity.PostComment;
import ru.skillbox.socnetwork.model.rqdto.NewPostDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.CommentDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.repository.PostCommentRepository;
import ru.skillbox.socnetwork.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository commentRepository;
    private final PersonService personService;

    public List<PostDto> getALl(int offset, int perPage) {
        return getPostDtoListOfAllPersons(postRepository.getAlreadyPostedWithOffset(offset, perPage));
    }

    public List<PostDto> getWall(int personId, int offset, int perPage) {
        List<Post> posts = postRepository.getByAuthorIdWithOffset(personId, offset, perPage);
        PersonDto personDto = new PersonDto(personService.getById(personId));
        return getPostDtoListOfOnePerson(posts, personDto);
    }

    public PostDto getById(int postId) {
        Post post = postRepository.getById(postId);
        PersonDto personDto = new PersonDto(personService.getById(post.getAuthor()));
        List<CommentDto> commentDtoList = getCommentDtoList(postId);
        return new PostDto(post, personDto, commentDtoList);
    }

    public void deletePostById(int postId) {
        postRepository.deleteById(postId);
    }

    public List<CommentDto> getCommentDtoList(int postId) {
        List<PostComment> postComments = commentRepository.getCommentsByPostId(postId);
        if (postComments == null) {
            return new ArrayList<>();
        }
        return postComments.stream().map(CommentDto::new).collect(Collectors.toList());
    }

    private List<PostDto> getPostDtoListOfOnePerson(List<Post> posts, PersonDto personDto) {
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : posts) {
            postDtoList.add(new PostDto(post, personDto, getCommentDtoList(post.getId())));
        }
        return postDtoList;
    }

    private List<PostDto> getPostDtoListOfAllPersons(List<Post> posts) {
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : posts) {
            postDtoList.add(new PostDto(post,
                    new PersonDto(personService.getById(post.getAuthor())),
                    getCommentDtoList(post.getId())));
        }
        return postDtoList;
    }

    public PostDto addPost(NewPostDto newPostDto) {
        Post post = postRepository.addPost(newPostDto);
        return new PostDto(post, new PersonDto(personService.getById(newPostDto.getAuthorId())), new ArrayList<>());
    }

    public PostDto editPost(int id, NewPostDto newPostDto) {
        postRepository.editPost(id, newPostDto);
        return getById(id);
    }

    public void updatePostLikeCount(Integer likes, Integer postId) {
        postRepository.updatePostLikeCount(likes, postId);
    }

    public CommentDto addCommentToPost(CommentDto comment) {
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

    public List<PostDto> choosePostsWhichContainsText(String text, long dateFrom, long dateTo) {
        List<Post> posts = postRepository.choosePostsWhichContainsText(text, dateFrom, dateTo);
        return getPostDtoListOfAllPersons(posts);

    }
}

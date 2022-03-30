package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.entity.PostComment;
import ru.skillbox.socnetwork.model.rsdto.PersonResponse;
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
        return getPostDtoListOfOnePerson(postRepository.getAllWithOffset(offset, perPage));
    }

    public List<PostDto> getWall(int personId, int offset, int perPage) {
        List<Post> posts = postRepository.getByAuthorIdWithOffset(personId, offset, perPage);
        PersonResponse personDto = new PersonResponse(personService.getById(personId));
        return getPostDtoListOfOnePerson(posts, personDto);
    }

    public PostDto getById(int postId) {
        Post post = postRepository.getById(postId);
        PersonResponse personDto = new PersonResponse(personService.getById(post.getAuthor()));
        List<CommentDto> commentDtoList = getCommentDtoList(postId);
        return new PostDto(post, personDto, commentDtoList);
    }

    /**
     * Пока возвращает только пост, не меняет
     * @param postId
     * @return
     */
    public PostDto updatePostByPostId(int postId) {
        Post post = postRepository.getById(postId);
        PersonResponse personDto = new PersonResponse(personService.getById(post.getAuthor()));
        List<CommentDto> commentDtoList = commentRepository
                .getByPostId(postId)
                .stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
        return new PostDto(post, personDto, commentDtoList);
    }

    public int deletePostById(int postId) {
        return postRepository.deleteById(postId);
    }

    private List<CommentDto> getCommentDtoList(int postId) {
        List<PostComment> postComments = commentRepository.getByPostId(postId);
        if (postComments == null) {
            return new ArrayList<>();
        }
        return postComments.stream().map(CommentDto::new).collect(Collectors.toList());
    }

    private List<PostDto> getPostDtoListOfOnePerson(List<Post> posts, PersonResponse personDto) {
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : posts) {
            postDtoList.add(new PostDto(post, personDto, getCommentDtoList(post.getId())));
        }
        return postDtoList;
    }

    private List<PostDto> getPostDtoListOfOnePerson(List<Post> posts) {
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : posts) {
            postDtoList.add(new PostDto(post,
                    new PersonResponse(personService.getById(post.getAuthor())),
                    getCommentDtoList(post.getId())));
        }
        return postDtoList;
    }
}

package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.controller.exception.BadRequestResponseEntity;
import ru.skillbox.socnetwork.model.rqdto.NewPostDto;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.CommentDto;
import ru.skillbox.socnetwork.model.rsdto.postdto.PostDto;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.PersonService;
import ru.skillbox.socnetwork.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
    private final PersonService personService;


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<PostDto>> getPostById(@PathVariable int id) {
        GeneralResponse<PostDto> response = new GeneralResponse<PostDto>();
        try {
            response.setData(postService.getById(id));
        } catch (EmptyResultDataAccessException e) {
            return new BadRequestResponseEntity("entity not found");
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<PostDto>> deletePostById(@PathVariable int id) {
        postService.deletePostById(id);
        GeneralResponse<PostDto> response = new GeneralResponse<>(new PostDto(id));
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<PostDto>> editPostById(@PathVariable int id, @RequestBody NewPostDto newPostDto) {
        GeneralResponse<PostDto> response = new GeneralResponse<>(postService.editPost(id, newPostDto));
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<List<CommentDto>>> getCommentsByPostId(@PathVariable int id) {
        GeneralResponse<List<CommentDto>> response = new GeneralResponse<>(postService.getCommentDtoList(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<CommentDto>> addCommentToPost(@PathVariable int id,
                                                                         @RequestBody CommentDto comment) {
        comment.setAuthor(new PersonDto(personService.getById(getSecurityUser().getId())));
        comment.setTime(System.currentTimeMillis());
        comment.setPostId(id);
        comment.setIsBlocked(false);
        GeneralResponse<CommentDto> response = new GeneralResponse<>(postService.addCommentToPost(comment));
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<CommentDto>> editCommentToPost(@PathVariable int id,
                                                                         @PathVariable int commentId,
                                                                         @RequestBody CommentDto comment) {
        comment.setId(commentId);
        GeneralResponse<CommentDto> response = new GeneralResponse<>(postService.editCommentToPost(comment));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}/comments/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse<CommentDto>> deleteCommentToPost(@PathVariable int id,
                                                                         @PathVariable int commentId) {
        GeneralResponse<CommentDto> response = new GeneralResponse<>(postService.deleteCommentToPost(commentId));
        return ResponseEntity.ok(response);
    }

    private SecurityUser getSecurityUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) auth.getPrincipal();
    }

    @GetMapping()
    public ResponseEntity<GeneralResponse<List<PostDto>>> searchPostByText(
            @RequestParam(value = "text") String text,
            @RequestParam(value = "date_from", defaultValue = "0", required = false) long dateFrom,
            @RequestParam(value = "date_to", defaultValue = "0", required = false) long dateTo,
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            @RequestParam(value = "author", defaultValue = "", required = false) String author,
            @RequestParam(value = "perPage", defaultValue = "20", required = false) int perPage) {
        GeneralResponse<List<PostDto>> response = new GeneralResponse<>
                (postService.choosePostsWhichContainsText(text, dateFrom, dateTo, author, perPage,
                        getSecurityUser().getId()));

        return ResponseEntity.ok(response);
    }
}

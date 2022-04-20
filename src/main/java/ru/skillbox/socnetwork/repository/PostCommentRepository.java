package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.PostComment;
import ru.skillbox.socnetwork.model.mapper.PostCommentMapper;
import ru.skillbox.socnetwork.model.rsdto.postdto.CommentDto;

import java.util.List;
@RequiredArgsConstructor
@Repository
public class PostCommentRepository {
    private final JdbcTemplate jdbc;

    public List<PostComment> getCommentsByPostId(int postId) {
        String sql = "SELECT * FROM post_comment WHERE post_id = ?";
        return jdbc.query(sql, new PostCommentMapper(), postId);
    }

    public void add(CommentDto comment) {
        if(comment.getParentId() == null) {
            String sql = "INSERT INTO post_comment (time, post_id, author_id, comment_text, is_blocked) values (?, ?, ?, ?, ?)";
            jdbc.update(sql, System.currentTimeMillis(), comment.getPostId(), comment.getAuthor().getId(), comment.getCommentText(), comment.getIsBlocked());
        } else {
            String sql = "INSERT INTO post_comment (time, post_id, author_id, comment_text, is_blocked, parent_id) values (?, ?, ?, ?, ?, ?)";
            jdbc.update(sql, System.currentTimeMillis(), comment.getPostId(), comment.getAuthor().getId(), comment.getCommentText(), comment.getIsBlocked(), comment.getParentId());
        }
    }

    public void edit(CommentDto comment) {
        String sql = "UPDATE post_comment set comment_text = ?, time = ? WHERE id = ?";
        jdbc.update(sql, comment.getCommentText(), System.currentTimeMillis(), comment.getId());
    }

    public void deleteById(int commentId) {
        String sql = "DELETE FROM post_comment WHERE id = ?";
        jdbc.update(sql, commentId);
    }

    public PostComment getById(int id) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM post_comment WHERE id = ?";
        return jdbc.queryForObject(sql, new PostCommentMapper(), id);
    }

    public void updateLikeCount(Integer likes, Integer postId) {
        String sql = "update post_comment set likes = ? where id = ?";
        jdbc.update(sql, likes, postId);
    }
}
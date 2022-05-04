package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.PostComment;
import ru.skillbox.socnetwork.model.mapper.PostCommentMapper;
import ru.skillbox.socnetwork.model.rsdto.postdto.CommentDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
@DebugLogs
public class PostCommentRepository {
    private final JdbcTemplate jdbc;

    public List<PostComment> getCommentsByPostId(int currentPersonId, int postId) {
        String sql = "select pc.*, (cl.person_id = ?) as is_liked " +
                "from post_comment pc " +
                "left join comment_like cl on cl.comment_id = pc.id and cl.person_id = ? " +
                "where pc.post_id = ? order by time";
        return jdbc.query(sql, new PostCommentMapper(), currentPersonId, currentPersonId, postId);
    }

    public void add(CommentDto comment) {
            String sql = "INSERT INTO post_comment (time, post_id, author_id, comment_text, is_blocked, parent_id) values (?, ?, ?, ?, ?, ?)";
            jdbc.update(sql, System.currentTimeMillis(), comment.getPostId(), comment.getAuthor().getId(), comment.getCommentText(), comment.getIsBlocked(), comment.getParentId());
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
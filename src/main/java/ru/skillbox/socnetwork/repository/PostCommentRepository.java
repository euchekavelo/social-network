package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.PostComment;
import ru.skillbox.socnetwork.model.mapper.PostCommentMapper;

import java.util.List;
@RequiredArgsConstructor
@Repository
public class PostCommentRepository {
    private final JdbcTemplate jdbc;

    public List<PostComment> getByPostId(int postId) {
        String sql = "SELECT * FROM post_comment WHERE id = ?";
        return jdbc.query(sql, new PostCommentMapper(), postId);
    }
}


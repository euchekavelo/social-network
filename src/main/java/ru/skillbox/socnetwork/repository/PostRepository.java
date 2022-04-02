package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.mapper.PersonMapper;
import ru.skillbox.socnetwork.model.mapper.PostMapper;
import ru.skillbox.socnetwork.model.rqdto.NewPostDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    private final JdbcTemplate jdbc;

    public List<Post> getAll(){
        return jdbc.query("select * from post", new PostMapper());
    }

    public List<Post> getAllWithOffset(int offset, int limit) {
        String sql = "SELECT * FROM post LIMIT ? OFFSET ?";
        return jdbc.query(sql, new PostMapper(), limit, offset);
    }

    public List<Post> getByAuthorIdWithOffset(int authorId,int offset, int limit) {
        String sql = "SELECT * FROM post WHERE author = ? LIMIT ? OFFSET ?";
        return jdbc.query(sql, new PostMapper(),authorId, limit, offset);
    }

    public Post getById(int id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        return jdbc.queryForObject(sql, new PostMapper(), id);
    }

    public int deleteById(int id) {
        String sql = "delete from post where id = ?";
        return jdbc.update(sql, id);
    }

    public Post addPost(NewPostDto newPostDto) {
        String sql = "insert into post (time, author_id, title, post_text) values\n" +
                "(?, ?, ?, ?)";
        jdbc.queryForObject(sql, new PostMapper(), newPostDto.getTime(), newPostDto.getAuthorId(), newPostDto.getTitle(), newPostDto.getPostText());
        return getLastPersonPost(newPostDto.getAuthorId());
    }

    public Post getLastPersonPost(int personId) {
        String sql = "select * from post where author = ? order by id desc limit 1";
        return jdbc.queryForObject(sql, new PostMapper(), personId);
    }
}

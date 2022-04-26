package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.Post;
import ru.skillbox.socnetwork.model.mapper.PostMapper;
import ru.skillbox.socnetwork.model.rqdto.NewPostDto;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    private final JdbcTemplate jdbc;

    public List<Post> getAll() {
        return jdbc.query("select * from post", new PostMapper());
    }

    public List<Post> getAlreadyPostedWithOffset(int offset, int limit) {
        String sql = "SELECT * FROM post WHERE time < ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new PostMapper(), System.currentTimeMillis(), limit, offset);
    }

    public List<Post> getByAuthorIdWithOffset(int authorId, int offset, int limit) {
        String sql = "SELECT * FROM post WHERE author = ? ORDER BY id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new PostMapper(), authorId, limit, offset);
    }

    public Post getById(int id) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM post WHERE id = ?";
        return jdbc.queryForObject(sql, new PostMapper(), id);
    }

    public int deleteById(int id) {
        String sql = "delete from post where id = ?";
        return jdbc.update(sql, id);
    }

    public Post getLastPersonPost(int personId) throws EmptyResultDataAccessException {
        String sql = "select * from post where author = ? order by id desc limit 1";
        return jdbc.queryForObject(sql, new PostMapper(), personId);
    }

    public Post addPost(NewPostDto newPostDto) {
        String sql = "insert into post (time, author, title, post_text, is_blocked) values (?, ?, ?, ?, ?)";
        jdbc.update(sql, newPostDto.getTime(), newPostDto.getAuthorId(), newPostDto.getTitle(), newPostDto.getPostText(), false);
        return getLastPersonPost(newPostDto.getAuthorId());
    }

    public void editPost(int id, NewPostDto newPostDto) {
        String sql = "update post set title = ?, post_text = ? where id = ?";
        jdbc.update(sql, newPostDto.getTitle(), newPostDto.getPostText(), id);
    }

    public void updatePostLikeCount(Integer likes, Integer postId) {
        String sql = "update post set likes = ? where id = ?";
        jdbc.update(sql, likes, postId);
    }

    public List<Post> choosePostsWhichContainsText(String text, long dateFrom, long dateTo, String authorName,
                                                   String authorSurname, int perPage) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", "%" + authorName + "%");
        parameters.addValue("surname", "%" + authorSurname + "%");
        parameters.addValue("text", "%" + text + "%");
        parameters.addValue("dateFrom", dateFrom);
        parameters.addValue("dateTo", dateTo);
        String sql = "select " +
                "post.id, " +
                "time, " +
                "author, " +
                "title, " +
                "post_text, " +
                "post.is_blocked, " +
                "likes " +
                "from post " +
                " join person on post.author = person.id" +
                " where ((first_name like :name and last_name like :surname)" +
                " or (first_name like :surname and last_name like :name))" +
                " and (post_text like :text or title like :text)" +
                " and time > :dateFrom" +
                " and time < :dateTo" +
                " and post.is_blocked = 'f'";

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbc);
        return template.query(sql, parameters, new PostMapper());
    }
}

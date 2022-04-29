package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.DeletedUser;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.mapper.DeletedUserMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeletedUsersRepository {

  private final JdbcTemplate jdbc;

  public void addDeletedUser(Person person){
    DeletedUser deletedUser = new DeletedUser(person);
    String sql = "insert into deleted_users (person_id, photo, first_name, last_name, expire) values (?, ?, ?, ?, ?)";
    jdbc.update(sql,
            deletedUser.getPersonId(),
            deletedUser.getPhoto(),
            deletedUser.getFirstName(),
            deletedUser.getLastName(),
            deletedUser.getExpire());
  }

  public List<DeletedUser> getAll(){
    return jdbc.query("select * from deleted_users", new DeletedUserMapper());
  }

  public List<DeletedUser> getAllExpired(){
    return jdbc.query("select * from deleted_users where expire < ?", new DeletedUserMapper());
  }

  public void delete(Integer id){
    String sql = "delete from deleted_users where id = ?";
    Object[] args = new Object[]{id};
    jdbc.update(sql, id);
  }
}
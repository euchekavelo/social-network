package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.TempToken;

@Repository
@RequiredArgsConstructor
public class TempTokenRepository {
  private final JdbcTemplate jdbc;

  public void addToken(TempToken token){
    String sql = "insert into temptoken (token, email) values (?, ?)";
    jdbc.update(sql, token.getToken(), token.getEmail());
  }

  public TempToken getToken(String token){
    String sql = "select * from temptoken where token = ?";
    return jdbc.queryForObject(sql, TempToken.class, token);
  }

  public void deleteToken(String token){
    String sql = "delete from temptoken where token = ?";
    Object[] args = new Object[] {token};
    jdbc.update(sql, token);
  }
}

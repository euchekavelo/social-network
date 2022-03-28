package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@RequiredArgsConstructor
@Repository
public class TagRepository {
    private final JdbcTemplate jdbc;
}

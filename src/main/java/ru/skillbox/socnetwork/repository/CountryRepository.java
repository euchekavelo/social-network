package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.controller.exception.InvalidRequestException;
import ru.skillbox.socnetwork.model.entity.Country;
import ru.skillbox.socnetwork.model.mapper.CountryMapper;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CountryRepository {

    private final JdbcTemplate jdbc;

    public int getIdByName(String name) {
        String sql = "select * from country where name = ?";
        Optional<Country> country = Optional
                .ofNullable(jdbc.queryForObject(sql, new CountryMapper(), name));
        return country.isPresent() ? country.get().getId() : 0;
    }

    public String getNameById(int id) {
        String sql = "select * from country where id = ?";
        Optional<Country> country = Optional
                .ofNullable(jdbc.queryForObject(sql, new CountryMapper(), id));
        return country.isPresent() ? country.get().getName() : "";

    }
}

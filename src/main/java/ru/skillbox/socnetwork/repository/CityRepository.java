package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.model.entity.City;
import ru.skillbox.socnetwork.model.entity.CityCountry;
import ru.skillbox.socnetwork.model.mapper.CityCountryMapper;
import ru.skillbox.socnetwork.model.mapper.CityMapper;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CityRepository {

    private final JdbcTemplate jdbc;

    public int getIdByName(String name) {
        String sql = "select * from country where name = ?";
        Optional<City> country = Optional
                .ofNullable(jdbc.queryForObject(sql, new CityMapper(), name));
        return country.isPresent() ? country.get().getId() : 0;
    }

    public String getNameById(int id) {
        String sql = "select * from country where id = ?";
        Optional<City> country = Optional
                .ofNullable(jdbc.queryForObject(sql, new CityMapper(), id));
        return country.isPresent() ? country.get().getName() : "";
    }

    public CityCountry getCityCountryById(int id) {
        String sql = "select * from citiCounty where id = ?";
        Optional<CityCountry> citiCounty = Optional
                .ofNullable(jdbc.queryForObject(sql, new CityCountryMapper(), id));
        return citiCounty.orElseGet(CityCountry::new);
    }
}

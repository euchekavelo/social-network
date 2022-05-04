package ru.skillbox.socnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.City;
import ru.skillbox.socnetwork.model.entity.CityCountry;
import ru.skillbox.socnetwork.model.entity.Country;
import ru.skillbox.socnetwork.model.mapper.CityCountryMapper;
import ru.skillbox.socnetwork.model.mapper.CityMapper;
import ru.skillbox.socnetwork.model.mapper.CountryMapper;

import java.util.List;

@RequiredArgsConstructor
@Repository
@DebugLogs
public class CityCountryRepository {

    private final JdbcTemplate jdbc;

    public List<City> getCityList() {
        String sql = "select city from person group by city";
        return jdbc.query(sql, new CityMapper());
    }

    public List<Country> getCountryList() {
        String sql = "select country from person group by country";
        return jdbc.query(sql, new CountryMapper());
    }

    public List<CityCountry> getCityCountry() {
        String sql = "select city, country from person group by city, country";
        return jdbc.query(sql, new CityCountryMapper());
    }
}

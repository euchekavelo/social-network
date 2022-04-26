package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.CityCountry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityCountryMapper implements RowMapper<CityCountry> {
    @Override
    public CityCountry mapRow(ResultSet rs, int rowNum) throws SQLException {
        CityCountry mapper = new CityCountry();
        mapper.setCountry(rs.getString("country"));
        mapper.setCity(rs.getString("city"));
        return mapper;
    }
}

package ru.skillbox.socnetwork.model.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.skillbox.socnetwork.model.entity.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        City mapper = new City();
        mapper.setName(rs.getString("city"));
        return mapper;
    }
}

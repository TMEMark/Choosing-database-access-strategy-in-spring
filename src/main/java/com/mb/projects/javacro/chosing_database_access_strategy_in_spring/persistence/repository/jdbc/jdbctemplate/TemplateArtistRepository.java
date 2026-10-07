package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class TemplateArtistRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ArtistModel> rowMapper = (rs, rowNum) -> new ArtistModel(
        rs.getObject(DATABASE_ID_FIELD, UUID.class),
        rs.getString(DATABASE_NAME_FIELD),
        rs.getString(DATABASE_COUNTRY_FIELD)
    );

    public TemplateArtistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(ArtistModel artist) {
        String sql = "INSERT INTO artist (id, name, country) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, artist.id(), artist.name(), artist.country());
    }

    public ArtistModel findById(UUID id) {
        String sql = "SELECT id, name, country FROM artist WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<ArtistModel> findAll() {
        String sql = "SELECT id, name, country FROM artist";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void update(ArtistModel artist) {
        String sql = "UPDATE artist SET name = ?, country = ? WHERE id = ?";
        jdbcTemplate.update(sql, artist.name(), artist.country(), artist.id());
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM artist WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
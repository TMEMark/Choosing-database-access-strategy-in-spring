package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.AlbumModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class TemplateAlbumRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AlbumModel> rowMapper = (rs, rowNum) -> new AlbumModel(
        rs.getObject(DATABASE_ID_FIELD, UUID.class),
        rs.getString(DATABASE_TITLE_FIELD),
        rs.getInt(DATABASE_RELEASE_YEAR_FIELD),
        rs.getObject(DATABASE_ARTIST_ID_FIELD, UUID.class)
    );

    public TemplateAlbumRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(AlbumModel album) {
        String sql = "INSERT INTO album (id, title, release_year, artist_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, album.id(), album.title(), album.releaseYear(), album.artistId());
    }

    public AlbumModel findById(UUID id) {
        String sql = "SELECT id, title, release_year, artist_id FROM album WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<AlbumModel> findAll() {
        String sql = "SELECT id, title, release_year, artist_id FROM album";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void update(AlbumModel album) {
        String sql = "UPDATE album SET title = ?, release_year = ?, artist_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, album.title(), album.releaseYear(), album.artistId(), album.id());
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM album WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
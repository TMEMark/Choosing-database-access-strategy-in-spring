package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.TrackModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class TemplateTrackRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<TrackModel> rowMapper = (rs, rowNum) -> new TrackModel(
        rs.getObject(DATABASE_ID_FIELD, UUID.class),
        rs.getString(DATABASE_TITLE_FIELD),
        rs.getInt(DATABASE_DURATION_SECONDS_FIELD),
        rs.getString(DATABASE_GENRE_NAME_FIELD),
        rs.getObject(DATABASE_ALBUM_ID_FIELD, UUID.class)
    );

    public TemplateTrackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(TrackModel track) {
        String sql = "INSERT INTO track (id, title, duration_seconds, genre_name, album_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, track.id(), track.title(), track.durationSeconds(), track.genreName(), track.albumId());
    }

    public TrackModel findById(UUID id) {
        String sql = "SELECT id, title, duration_seconds, genre_name, album_id FROM track WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<TrackModel> findAll() {
        String sql = "SELECT id, title, duration_seconds, genre_name, album_id FROM track";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void update(TrackModel track) {
        String sql = "UPDATE track SET title = ?, duration_seconds = ?, genre_name = ?, album_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, track.title(), track.durationSeconds(), track.genreName(), track.albumId(), track.id());
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM track WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
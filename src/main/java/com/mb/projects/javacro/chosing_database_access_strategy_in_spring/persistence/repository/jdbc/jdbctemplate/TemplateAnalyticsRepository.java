package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistEngagementMetrics;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class TemplateAnalyticsRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TemplateAnalyticsRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ArtistEngagementMetrics> getArtistAnalyticsJdbc(String genre, Integer minReleaseYear) {
        String sql = """
        SELECT
            a.id AS artist_id,
            a.name AS artist_name,
            a.country,
            COUNT(h.id) AS total_listens,
            COALESCE(SUM(t.duration_seconds) / 60, 0) AS total_duration_minutes,
            COUNT(DISTINCT h.user_id) AS unique_listeners,
            (SELECT COUNT(DISTINCT tr.id) 
             FROM albums al2 
             JOIN tracks tr ON al2.id = tr.album_id 
             WHERE al2.artist_id = a.id) AS total_tracks_count
        FROM artists a
        JOIN albums al ON a.id = al.artist_id
        JOIN tracks t ON al.id = t.album_id
        LEFT JOIN user_listen_history h ON t.id = h.track_id
        WHERE (:genre IS NULL OR t.genre_name = :genre)
          AND (:minReleaseYear IS NULL OR al.release_year >= :minReleaseYear)
        GROUP BY a.id, a.name, a.country
        ORDER BY total_listens DESC
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("genre", (genre == null || genre.isBlank()) ? null : genre)
                .addValue("minReleaseYear", minReleaseYear);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> new ArtistEngagementMetrics(
                rs.getObject("artist_id", UUID.class),
                rs.getString("artist_name"),
                rs.getString("country"),
                rs.getLong("total_listens"),
                rs.getLong("total_duration_minutes"),
                rs.getLong("unique_listeners"),
                rs.getLong("total_tracks_count")
        ));
    }
}

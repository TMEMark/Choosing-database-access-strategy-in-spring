package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistAnalyticsReportModel;
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

    public List<ArtistAnalyticsReportModel> getArtistAnalytics(
            String genre,
            Integer minReleaseYear,
            String sortBy,
            String sortOrder
    ) {
        // Safe column whitelist to prevent SQL injection during dynamic sorting
        String safeSortColumn = switch (sortBy != null ? sortBy.toLowerCase() : "") {
            case "listens" -> "total_listens";
            case "duration" -> "total_duration_minutes";
            case "listeners" -> "unique_listeners";
            default -> "artist_name";
        };

        String safeDirection = "DESC".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";

        // Advanced SQL: CTEs, Subqueries with Window Functions, Multi-Table Joins & Grouping
        String sql ="""
            WITH RankedTracks AS (
                SELECT
                    a.id AS artist_id,
                    t.title AS track_title,
                    COUNT(h.id) AS track_listens,
                    ROW_NUMBER() OVER (PARTITION BY a.id ORDER BY COUNT(h.id) DESC) as rank
                FROM artists a
                JOIN albums al ON a.id = al.artist_id
                JOIN tracks t ON al.id = t.album_id
                LEFT JOIN user_listen_history h ON t.id = h.track_id
                WHERE (:genre IS NULL OR t.genre_name = :genre)
                  AND (:minReleaseYear IS NULL OR al.release_year >= :minReleaseYear)
                GROUP BY a.id, t.id, t.title
            ),
            TopTracks AS (
                SELECT artist_id, track_title
                FROM RankedTracks
                WHERE rank = 1
            )
            SELECT
                a.id AS artist_id,
                a.name AS artist_name,
                a.country,
                COUNT(h.id) AS total_listens,
                COALESCE(SUM(t.duration_seconds) / 60, 0) AS total_duration_minutes,
                COUNT(DISTINCT h.user_id) AS unique_listeners,
                COALESCE(tt.track_title, 'N/A') AS top_track_title,
                STRING_AGG(DISTINCT t.genre_name, ', ') AS associated_genres
            FROM artists a
            JOIN albums al ON a.id = al.artist_id
            JOIN tracks t ON al.id = t.album_id
            LEFT JOIN user_listen_history h ON t.id = h.track_id
            LEFT JOIN TopTracks tt ON a.id = tt.artist_id
            WHERE (:genre IS NULL OR t.genre_name = :genre)
              AND (:minReleaseYear IS NULL OR al.release_year >= :minReleaseYear)
            GROUP BY a.id, a.name, a.country, tt.track_title
            ORDER BY %s %s
            """.formatted(safeSortColumn, safeDirection);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("genre", (genre == null || genre.isBlank()) ? null : genre)
                .addValue("minReleaseYear", minReleaseYear);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> new ArtistAnalyticsReportModel(
                rs.getObject("artist_id", UUID.class),
                rs.getString("artist_name"),
                rs.getString("country"),
                rs.getLong("total_listens"),
                rs.getLong("total_duration_minutes"),
                rs.getLong("unique_listeners"),
                rs.getString("top_track_title"),
                rs.getString("associated_genres")
        ));
    }
}

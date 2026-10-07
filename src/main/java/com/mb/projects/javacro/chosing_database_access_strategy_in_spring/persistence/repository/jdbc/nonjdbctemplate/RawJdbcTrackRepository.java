package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.TrackModel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class RawJdbcTrackRepository {

    private final DataSource dataSource;

    public RawJdbcTrackRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(TrackModel track) {
        String sql = "INSERT INTO track (id, title, duration_seconds, genre_name, album_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, track.id());
            pstmt.setString(2, track.title());
            pstmt.setInt(3, track.durationSeconds());
            pstmt.setString(4, track.genreName());
            pstmt.setObject(5, track.albumId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create track", e);
        }
    }

    public TrackModel findById(UUID id) {
        String sql = "SELECT id, title, duration_seconds, genre_name, album_id FROM track WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch track by ID", e);
        }
        return null;
    }

    public List<TrackModel> findAll() {
        String sql = "SELECT id, title, duration_seconds, genre_name, album_id FROM track";
        List<TrackModel> tracks = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tracks.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all tracks", e);
        }
        return tracks;
    }

    public void update(TrackModel track) {
        String sql = "UPDATE track SET title = ?, duration_seconds = ?, genre_name = ?, album_id = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, track.title());
            pstmt.setInt(2, track.durationSeconds());
            pstmt.setString(3, track.genreName());
            pstmt.setObject(4, track.albumId());
            pstmt.setObject(5, track.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update track", e);
        }
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM track WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete track", e);
        }
    }

    private TrackModel mapRow(ResultSet rs) throws SQLException {
        return new TrackModel(
            rs.getObject(DATABASE_ID_FIELD, UUID.class),
            rs.getString(DATABASE_TITLE_FIELD),
            rs.getInt(DATABASE_DURATION_SECONDS_FIELD),
            rs.getString(DATABASE_GENRE_NAME_FIELD),
            rs.getObject(DATABASE_ALBUM_ID_FIELD, UUID.class)
        );
    }
}
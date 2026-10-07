package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.AlbumModel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class RawJdbcAlbumRepository {

    private final DataSource dataSource;

    public RawJdbcAlbumRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(AlbumModel album) {
        String sql = "INSERT INTO album (id, title, release_year, artist_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, album.id());
            pstmt.setString(2, album.title());
            pstmt.setInt(3, album.releaseYear());
            pstmt.setObject(4, album.artistId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create album", e);
        }
    }

    public AlbumModel findById(UUID id) {
        String sql = "SELECT id, title, release_year, artist_id FROM album WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch album by ID", e);
        }
        return null;
    }

    public List<AlbumModel> findAll() {
        String sql = "SELECT id, title, release_year, artist_id FROM album";
        List<AlbumModel> albums = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                albums.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all albums", e);
        }
        return albums;
    }

    public void update(AlbumModel album) {
        String sql = "UPDATE album SET title = ?, release_year = ?, artist_id = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, album.title());
            pstmt.setInt(2, album.releaseYear());
            pstmt.setObject(3, album.artistId());
            pstmt.setObject(4, album.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update album", e);
        }
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM album WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete album", e);
        }
    }

    private AlbumModel mapRow(ResultSet rs) throws SQLException {
        return new AlbumModel(
            rs.getObject(DATABASE_ID_FIELD, UUID.class),
            rs.getString(DATABASE_TITLE_FIELD),
            rs.getInt(DATABASE_RELEASE_YEAR_FIELD),
            rs.getObject(DATABASE_ARTIST_ID_FIELD, UUID.class)
        );
    }
}
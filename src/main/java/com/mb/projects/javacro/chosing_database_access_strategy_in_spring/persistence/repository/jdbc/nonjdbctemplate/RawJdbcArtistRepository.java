package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.ArtistModel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class RawJdbcArtistRepository {

    private final DataSource dataSource;

    public RawJdbcArtistRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(ArtistModel artist) {
        String sql = "INSERT INTO artist (id, name, country) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, artist.id());
            pstmt.setString(2, artist.name());
            pstmt.setString(3, artist.country());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert artist", e);
        }
    }

    public ArtistModel findById(UUID id) {
        String sql = "SELECT id, name, country FROM artist WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artist by ID", e);
        }
        return null;
    }

    public List<ArtistModel> findAll() {
        String sql = "SELECT id, name, country FROM artist";
        List<ArtistModel> artists = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                artists.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all artists", e);
        }
        return artists;
    }

    public void update(ArtistModel artist) {
        String sql = "UPDATE artist SET name = ?, country = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, artist.name());
            pstmt.setString(2, artist.country());
            pstmt.setObject(3, artist.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update artist", e);
        }
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM artist WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artist", e);
        }
    }

    private ArtistModel mapRow(ResultSet rs) throws SQLException {
        return new ArtistModel(
            rs.getObject(DATABASE_ID_FIELD, UUID.class),
            rs.getString(DATABASE_NAME_FIELD),
            rs.getString(DATABASE_COUNTRY_FIELD)
        );
    }
}
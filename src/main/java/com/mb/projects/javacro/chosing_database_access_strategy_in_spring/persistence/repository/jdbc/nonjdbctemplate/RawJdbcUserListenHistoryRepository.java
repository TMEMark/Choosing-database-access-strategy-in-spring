package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.nonjdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.UserListenHistoryModel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class RawJdbcUserListenHistoryRepository {

    private final DataSource dataSource;

    public RawJdbcUserListenHistoryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(UserListenHistoryModel history) {
        String sql = "INSERT INTO user_listen_history (id, user_id, track_id, device_type, listened_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, history.id());
            pstmt.setObject(2, history.userId());
            pstmt.setObject(3, history.trackId());
            pstmt.setString(4, history.deviceType());
            pstmt.setTimestamp(5, history.listenedAt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create listen history record", e);
        }
    }

    public UserListenHistoryModel findById(UUID id) {
        String sql = "SELECT id, user_id, track_id, device_type, listened_at FROM user_listen_history WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch listen history by ID", e);
        }
        return null;
    }

    public List<UserListenHistoryModel> findAll() {
        String sql = "SELECT id, user_id, track_id, device_type, listened_at FROM user_listen_history";
        List<UserListenHistoryModel> historyList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                historyList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all listen history records", e);
        }
        return historyList;
    }

    public void update(UserListenHistoryModel history) {
        String sql = "UPDATE user_listen_history SET user_id = ?, track_id = ?, device_type = ?, listened_at = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, history.userId());
            pstmt.setObject(2, history.trackId());
            pstmt.setString(3, history.deviceType());
            pstmt.setTimestamp(4, history.listenedAt());
            pstmt.setObject(5, history.id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update listen history record", e);
        }
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM user_listen_history WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete listen history record", e);
        }
    }

    private UserListenHistoryModel mapRow(ResultSet rs) throws SQLException {
        return new UserListenHistoryModel(
                rs.getObject(DATABASE_ID_FIELD, UUID.class),
                rs.getObject(DATABASE_USER_ID_FIELD, UUID.class),
                rs.getObject(DATABASE_TRACK_ID_FIELD, UUID.class),
                rs.getString(DATABASE_DEVICE_TYPE_FIELD),
                rs.getTimestamp(DATABASE_LISTENED_AT_FIELD)
        );
    }
}
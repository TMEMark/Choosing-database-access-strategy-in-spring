package com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.repository.jdbc.jdbctemplate;

import com.mb.projects.javacro.chosing_database_access_strategy_in_spring.persistence.model.UserListenHistoryModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.mb.projects.javacro.chosing_database_access_strategy_in_spring.common.DatabaseStrategyConstants.*;

@Repository
public class TemplateUserListenHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<UserListenHistoryModel> rowMapper = (rs, rowNum) -> new UserListenHistoryModel(
        rs.getObject(DATABASE_ID_FIELD, UUID.class),
        rs.getObject(DATABASE_USER_ID_FIELD, UUID.class),
        rs.getObject(DATABASE_TRACK_ID_FIELD, UUID.class),
        rs.getString(DATABASE_DEVICE_TYPE_FIELD),
        rs.getTimestamp(DATABASE_LISTENED_AT_FIELD)
    );

    public TemplateUserListenHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(UserListenHistoryModel history) {
        String sql = "INSERT INTO user_listen_history (id, user_id, track_id, device_type, listened_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, history.id(), history.userId(), history.trackId(), history.deviceType(), history.listenedAt());
    }

    public UserListenHistoryModel findById(UUID id) {
        String sql = "SELECT id, user_id, track_id, device_type, listened_at FROM user_listen_history WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<UserListenHistoryModel> findAll() {
        String sql = "SELECT id, user_id, track_id, device_type, listened_at FROM user_listen_history";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void update(UserListenHistoryModel history) {
        String sql = "UPDATE user_listen_history SET user_id = ?, track_id = ?, device_type = ?, listened_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, history.userId(), history.trackId(), history.deviceType(), history.listenedAt(), history.id());
    }

    public void deleteById(UUID id) {
        String sql = "DELETE FROM user_listen_history WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
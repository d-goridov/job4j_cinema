package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgresSessionRepository implements SessionRepository {
    private static final String GET_ALL = "SELECT * FROM sessions";
    private static final String GET_BY_ID = "SELECT * FROM sessions WHERE id = ?";
    private static final Logger LOG = LoggerFactory.getLogger(PostgresSessionRepository.class.getName());
    private final DataSource pool;


    public PostgresSessionRepository(DataSource pool) {
        this.pool = pool;
    }

    private Session createSession(ResultSet rs) throws SQLException {
        return new Session(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(GET_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(createSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return sessions;
    }

    @Override
    public Session findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(GET_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createSession(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}

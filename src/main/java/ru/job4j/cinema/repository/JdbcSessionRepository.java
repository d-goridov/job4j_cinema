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

/**
 * Реализация хранилища сеансов на основе DB Postgres
 */
@Repository
public class JdbcSessionRepository implements SessionRepository {
    /**
     * Строка - SQL запрос к БД, для получения всех сеансов
     */
    private static final String GET_ALL = "SELECT * FROM sessions";
    /**
     * Строка - SQL запрос к БД, для поиска сеанса по идентификатору
     */
    private static final String GET_BY_ID = "SELECT * FROM sessions WHERE id = ?";
    /**
     * Логгер
     */
    private static final Logger LOG = LoggerFactory.getLogger(JdbcSessionRepository.class.getName());
    /**
     * Объект используются для получения соединения с БД
     */
    private final DataSource dataSource;

    /**
     * Конструктор объекта хранилище сеансов
     * @param dataSource - объект для получения соединения с БД
     */
    public JdbcSessionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Session createSession(ResultSet rs) throws SQLException {
        return new Session(rs.getInt("id"), rs.getString("name"));
    }

    /**
     * Метод поиска всех сеансов в DB
     * @return - список сеансов
     */
    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
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

    /**
     * Метод поиска сеанса в DB по идентификатору
     * @param id - идентификатор сеанса
     * @return - сеанс, если поиск успешный, иначе - null
     */
    @Override
    public Session findById(int id) {
        try (Connection cn = dataSource.getConnection();
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

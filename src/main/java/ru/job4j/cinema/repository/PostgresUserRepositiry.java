package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Реализация хранилища пользователей на основе DB Postgres
 */
@Repository
public class PostgresUserRepositiry implements UserRepository {
    /**
     * Строка - SQL запрос к БД, для добавления пользователя в таблицу
     */
    private static final String INSERT = "INSERT INTO users(username, password, email, phone)"
            + " VALUES (?, ?, ?, ?)";

    /**
     * Строка - SQL запрос к БД, для поиска пользователя по электронной почте и паролю
     */
    private static final String GET_BY_EMAIL_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";

    /**
     * Логгер
     */
    private static final Logger LOG = LoggerFactory.getLogger(PostgresUserRepositiry.class.getName());

    /**
     * Объект используются для получения пула соединений с БД
     */
    private final DataSource pool;

    /**
     * Конструктор объекта хранилище пользователей
     * @param pool - пул соединений с DB
     */
    public PostgresUserRepositiry(DataSource pool) {
        this.pool = pool;
    }

    /**
     * Метод создает объект из результата запроса к БД
     * @param resultset - объект обеспечивает доступ к результату запроса к БД
     * @return - объект пользователя
     * @throws SQLException - при ошибке во время взаимодействия с БД
     */
    private User userOf(ResultSet resultset) throws SQLException {
        return new User(resultset.getInt("id"),
                resultset.getString("username"),
                resultset.getString("password"),
                resultset.getString("email"),
                resultset.getString("phone"));
    }

    /**
     * Метод сохранения пользователя в DB
     * @param user - сохраняемый пользователь
     * @return Optional.of(user) при успешном сохранении, иначе Optional.empty()
     */
    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    rsl = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * Метод поиска пользователя в DB по электронной почте и паролю
     * @param email - электронная почта пользователя
     * @param password - пароль пользователя
     * @return Optional.of(user) при успешном поиске, иначе Optional.empty()
     */
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(GET_BY_EMAIL_PASSWORD)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(userOf(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }
}

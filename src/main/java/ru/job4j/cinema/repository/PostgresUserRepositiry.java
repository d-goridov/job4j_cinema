package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class PostgresUserRepositiry implements UserRepository {
    private static final String INSERT = "INSERT INTO users(username, password, email, phone)"
            + " VALUES (?, ?, ?, ?)";

    private static final String GET_BY_EMAIL_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";

    private static final Logger LOG = LoggerFactory.getLogger(PostgresUserRepositiry.class.getName());

    private final BasicDataSource pool;

    public PostgresUserRepositiry(BasicDataSource pool) {
        this.pool = pool;
    }

    private User userOf(ResultSet resultset) throws SQLException {
        return new User(resultset.getInt("id"),
                resultset.getString("username"),
                resultset.getString("password"),
                resultset.getString("email"),
                resultset.getString("phone"));
    }

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

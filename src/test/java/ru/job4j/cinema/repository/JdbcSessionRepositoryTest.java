package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class JdbcSessionRepositoryTest {

    private Properties loadProperties() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Main.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }


    private BasicDataSource createPool() {
        Properties cfg = loadProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    @Test
    public void whenfindAllSessions() {
        SessionRepository repository = new JdbcSessionRepository(this.createPool());
        List<Session> sessions = repository.findAll();
        assertThat(sessions.size()).isEqualTo(8);

    }

    @Test
    public void whenFindSessionById() {
        SessionRepository repository = new JdbcSessionRepository(this.createPool());
        Session session = repository.findById(3);
        assertThat(session.getName()).isEqualTo("galapagoses");
    }
}
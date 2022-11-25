package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class PostgresUserRepositiryTest {

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
    public void whenAddUser() {
        UserRepository repository = new PostgresUserRepositiry(this.createPool());
        User user = new User(1, "Tom", "password", "tom123@gmail.com", "222-33-44");
        Optional<User> userDB = repository.add(user);
        assertThat(userDB.isPresent()).isTrue();
        User inDb = userDB.get();
        assertThat(inDb.getName()).isEqualTo(user.getName());
    }

    @Test
    public void whenFindByEmailAndPassword() {
        UserRepository repository = new PostgresUserRepositiry(this.createPool());
        User user = new User(2, "Oleg", "password", "oleg123@gmail.com", "222-55-44");
        Optional<User> userDB = repository.add(user);
        assertThat(userDB.isPresent()).isTrue();
        Optional<User> inDB = repository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(inDB.isPresent()).isTrue();
        User userInDB = inDB.get();
        assertThat(user.getPassword()).isEqualTo(userInDB.getPassword());
        assertThat(user.getEmail()).isEqualTo(userInDB.getEmail());
    }

    @Test
    public void whenNotAddSameUser() {
        User user = new User(3, "Elena", "password", "elena@gmail.com", "22-33-44");
        User user2 = new User(4, "Elena", "password1", "elena@gmail.com", "333-555");
        UserRepository repository = new PostgresUserRepositiry(this.createPool());
        repository.add(user);
        Optional<User> userInDB = repository.add(user2);
        Assertions.assertTrue(userInDB.isEmpty());
    }
}
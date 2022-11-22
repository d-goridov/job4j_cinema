package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostgresSessionRepositoryTest {

    @Test
    public void whenfindAllSessions() {
        SessionRepository repository = new PostgresSessionRepository(new Main().loadPool());
        List<Session> sessions = repository.findAll();
        assertThat(sessions.size()).isEqualTo(8);

    }

    @Test
    public void whenFindSessionById() {
        SessionRepository repository = new PostgresSessionRepository(new Main().loadPool());
        Session session = repository.findById(3);
        assertThat(session.getName()).isEqualTo("galapagoses");
    }
}
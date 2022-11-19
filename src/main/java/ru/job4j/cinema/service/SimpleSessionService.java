package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;

@Service
@ThreadSafe
public class SimpleSessionService implements SessionService {
    private final SessionRepository repository;

    public SimpleSessionService(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Session> findAll() {
       return repository.findAll();
    }

    @Override
    public Session findById(int id) {
        return repository.findById(id);
    }
}

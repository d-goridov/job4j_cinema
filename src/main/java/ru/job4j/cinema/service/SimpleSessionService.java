package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;

/**
 * Реализация сервиса по работе с сеансами
 */
@Service
public class SimpleSessionService implements SessionService {
    /**
     * Объект хранилища
     */
    private final SessionRepository repository;

    public SimpleSessionService(SessionRepository repository) {
        this.repository = repository;
    }

    /**
     * Метод выполняет поиск всех сеансов в хранилище,
     * возвращает результат поиска в виде списка
     * @return - список сеансов
     */
    @Override
    public List<Session> findAll() {
       return repository.findAll();
    }

    /**
     * Метод выполняет поиск сеанса в хранилище по идентификатору
     * @param id - идентификатор сеанса
     * @return - найденный сеанс
     */
    @Override
    public Session findById(int id) {
        return repository.findById(id);
    }
}

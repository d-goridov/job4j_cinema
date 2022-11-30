package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;

/**
 * Хранилище сеансов
 * @see ru.job4j.cinema.model.Session
 */
public interface SessionRepository {
    /**
     * Метод выполняет посик всех сеансов, возвращает
     * результат поиска в виде списка
     * @return - список сеансов
     */
    List<Session> findAll();

    /**
     * Метод выполняет поиск сеанса по идентификатору
     * @param id - идентификатор сеанса
     * @return - найденный сеанс
     */
    Session findById(int id);
}

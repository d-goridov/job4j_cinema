package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;

/**
 * Интерфейс описывает бизнес логику приложения
 * по работе с сеансами
 */
public interface SessionService {
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

    /**
     * Метод для получения массива байт из
     * файла изображения
     * @param sessionId - идентификатор сеанса
     * @return - массив байт
     */
    byte[] getSessionPhoto(int sessionId);
}

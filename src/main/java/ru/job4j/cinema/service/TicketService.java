package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

/**
 * Интерфейс описывает бизнес логику приложения
 * с билетами
 */
public interface TicketService {
    /**
     * Метод выполняет сохранение билета. При успешном сохранении возвращает
     * Optional с объектом билета. Иначе возвращает Optional.empty(). Сохранение
     * может не произойти, если такой билет уже куплен
     * @param ticket - сохраняемый пользователь
     * @return Optional.of(ticket) при успешном сохранении, иначе Optional.empty()
     */
    Optional<Ticket> add(Ticket ticket);
}

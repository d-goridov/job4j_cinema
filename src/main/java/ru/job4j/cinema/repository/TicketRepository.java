package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

/**
 * Хранилище билетов
 * @see ru.job4j.cinema.model.Ticket
 */
public interface TicketRepository {
    /**
     * Метод выполняет сохранение билета. При успешном сохранении возвращает
     * Optional с объектом билета. Иначе возвращает Optional.empty(). Сохранение
     * может не произойти, если такой билет уже куплен
     * @param ticket - сохраняемый пользователь
     * @return Optional.of(ticket) при успешном сохранении, иначе Optional.empty()
     */
    Optional<Ticket> add(Ticket ticket);
}

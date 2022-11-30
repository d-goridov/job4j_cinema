package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    /**
     * Объект хранилища
     */
    private final TicketRepository repository;

    public SimpleTicketService(TicketRepository repository) {
        this.repository = repository;
    }

    /**
     * Метод выполняет сохранение билета в хранилище. При успешном сохранении возвращает
     * Optional с объектом билета. Иначе возвращает Optional.empty(). Сохранение
     * может не произойти, если такой билет уже куплен
     * @param ticket - сохраняемый пользователь
     * @return Optional.of(ticket) при успешном сохранении, иначе Optional.empty()
     */
    @Override
    public Optional<Ticket> add(Ticket ticket) {
        return repository.add(ticket);
    }
}

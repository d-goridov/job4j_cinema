package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

@Service
@ThreadSafe
public class SimpleTicketService implements TicketService {

    private final TicketRepository repository;

    public SimpleTicketService(TicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Ticket> add(Ticket ticket) {
        return repository.add(ticket);
    }
}

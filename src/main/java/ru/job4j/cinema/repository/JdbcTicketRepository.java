package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Реализация хранилища билетов на основе DB Postgres
 */
@Repository
public class JdbcTicketRepository implements TicketRepository {

    /**
     * Строка - SQL запрос к БД, для добавления билета в таблицу
     */
    private static final String INSERT = "INSERT INTO tickets (session_id, pos_row, cell, user_id)"
            + " VALUES (?, ?, ?, ?)";

    /**
     * Логгер
     */
    private static final Logger LOG = LoggerFactory.getLogger(JdbcTicketRepository.class.getName());

    /**
     * Объект используются для получения соединения с БД
     */
    private final DataSource dataSource;

    /**
     * Конструктор объекта хранилище билетов
     * @param dataSource - объект для получения соединения с БД
     */
    public JdbcTicketRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Метод сохранения билета в DB
     * @param ticket - сохраняемый билет
     * @return Optional.of(ticket) при успешном сохранении, иначе Optional.empty()
     */
    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> rsl = Optional.empty();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSession().getId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUser().getId());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                    rsl = Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }
}

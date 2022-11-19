package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class PostgresTicketRepository implements TicketRepository{

    private static final String INSERT = "INSERT INTO tickets (session_id, pos_row, cell, user_id)"
            + " VALUES (?, ?, ?, ?)";

    private static final Logger LOG = LoggerFactory.getLogger(PostgresTicketRepository.class.getName());

    private final BasicDataSource pool;

    public PostgresTicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
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

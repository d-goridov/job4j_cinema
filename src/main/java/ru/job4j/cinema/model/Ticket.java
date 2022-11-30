package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных билета
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ticket {
    /**
     * Идентификатор билета
     */
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Ссылка на сеанс, на который куплен билет
     */
    private Session session;
    /**
     * Номер ряда билета
     */
    private int row;
    /**
     * Номер места билета
     */
    private int cell;
    /**
     * Ссылка на пользователя, который купил билет
     */
    private User user;
}

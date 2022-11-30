package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных сеанса
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Session {
    /**
     * Идентификатор сеанса
     */
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Название сеанса
     */
    private String name;
    /**
     * Изображение сеанса
     */
    private byte[] photo;

    public Session(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

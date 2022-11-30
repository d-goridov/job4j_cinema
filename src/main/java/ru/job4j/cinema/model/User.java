package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    /**
     * Идентификатор пользователя
     */
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Имя пользователя
     */
    private String name;
    /**
     * Пароль пользователя
     */
    private String password;
    /**
     * Электронная почта пользователя
     */
    private String email;
    /**
     * Номер телефона пользователя
     */
    private String phone;
}

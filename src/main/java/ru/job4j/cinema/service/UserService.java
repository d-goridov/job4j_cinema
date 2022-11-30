package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Интерфейс описывает бизнес логику работы приложения с пользователем
 */
public interface UserService {
    /**
     * Метод сохранения пользователя.
     * @param user - сохраняемый пользователь
     * @return Optional.of(user) при успешном сохранении, иначе Optional.empty()
     */
    Optional<User> add(User user);

    /**
     * Метод поиска пользователя по электронной почте и паролю
     * @param email - электронная почта пользователя
     * @param password - пароль пользователя
     * @return Optional.of(user) при успешном поиске, иначе Optional.empty()
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);
}

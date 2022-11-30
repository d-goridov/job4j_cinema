package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

/**
 * Класс описывает бизнес-логику приложения по
 * работе с пользователем. Является потокобезопасным,
 * т.к. проблема возникающая при добавлении одинаковых
 * пользователей в методе add() решена на уровне БД c помощью unique
 * constraint полей email и phone у сущности User.
 * @author Dmitriy Goridov
 * @version 1.0
 */
@Service
@ThreadSafe
public class SimpleUserService implements UserService {
    /**
     * Объект хранилища пользователей
     */
    private final UserRepository repositiry;

    public SimpleUserService(UserRepository repositiry) {
        this.repositiry = repositiry;
    }

    /**
     * Метод сохранения пользователя в хранилище.
     * @param user - сохраняемый пользователь
     * @return Optional.of(user) при успешном сохранении, иначе Optional.empty()
     */
    public Optional<User> add(User user) {
        return repositiry.add(user);
    }

    /**
     * Метод поиска в хранилище пользователя по электронной почте и паролю
     * @param email - электронная почта пользователя
     * @param password - пароль пользователя
     * @return Optional.of(user) при успешном поиске, иначе Optional.empty()
     */
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return repositiry.findUserByEmailAndPassword(email, password);
    }
}

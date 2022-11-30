package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Хранилище пользователей
 * @see ru.job4j.cinema.model.User
 */
public interface UserRepository {
     /**
      * Метод выполняет сохранение пользователя.При успешном сохранении возвращает
      * Optional с объектом пользователя. Иначе возвращает Optional.empty(). Сохранение
      * может не произойти, если сохраняется пользователь у которого электронная почта
      * или номер телефона совпадает с такими же значениями другого пользователя.
      * @param user - сохраняемый пользователь
      * @return Optional.of(user) при успешном сохранении, иначе Optional.empty()
      */
     Optional<User> add(User user);

     /**
      * Метод выполняет поиск в хранилище пользователя. При авторизации на сайте
      * когда мы вводим данные зарегистрированного пользователя то возвращается
      * Optional с объектом пользователя, иначе - Optional.empty().
      * @param email - электронная почта пользователя
      * @param password - пароль пользователя
      * @return Optional.of(user) при успешном поиске, иначе Optional.empty()
      */
     Optional<User> findUserByEmailAndPassword(String email, String password);
}

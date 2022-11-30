package ru.job4j.cinema.util;

import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

/**
 * Утилитарный класс
 */
public class UserSession {

    private UserSession() {
    }

    /**
     * Метод предназначен для получения объекта
     * User из HttpSession. Если в HttpSession нет объекта User,
     * то мы создаем объект User с анонимным пользователем.
     * @param session - объект HttpSession
     * @return - объект User, если в сессии есть объект, иначе -
     * анонимный пользователь с именем Гость
     */
    public static User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}

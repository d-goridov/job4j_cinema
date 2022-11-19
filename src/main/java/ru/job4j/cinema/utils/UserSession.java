package ru.job4j.cinema.utils;

import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

public class UserSession {

    private UserSession() {
    }

    public static User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}

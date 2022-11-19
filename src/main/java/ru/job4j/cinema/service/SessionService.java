package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;

public interface SessionService {
    List<Session> findAll();

    Session findById(int id);
}

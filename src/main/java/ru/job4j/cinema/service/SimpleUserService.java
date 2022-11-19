package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;


@Service
@ThreadSafe
public class SimpleUserService implements UserService {
    private final UserRepository repositiry;

    public SimpleUserService(UserRepository repositiry) {
        this.repositiry = repositiry;
    }

    public Optional<User> add(User user) {
        return repositiry.add(user);
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return repositiry.findUserByEmailAndPhone(email, phone);
    }
}

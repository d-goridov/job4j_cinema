package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Disabled
class PostgresUserRepositiryTest {

    @Test
    public void whenAddUser() {
        UserRepository repository = new PostgresUserRepositiry(new Main().loadPool());
        User user = new User(1, "Tom", "tom123@gmail.com", "222-33-44");
        Optional<User> userDB = repository.add(user);
        assertThat(userDB.isPresent()).isTrue();
        User inDb = userDB.get();
        assertThat(inDb.getName()).isEqualTo(user.getName());
    }

    @Test
    public void whenFindByEmailAndPhone() {
        UserRepository repository = new PostgresUserRepositiry(new Main().loadPool());
        User user = new User(2, "Oleg", "oleg123@gmail.com", "222-55-44");
        Optional<User> userDB = repository.add(user);
        assertThat(userDB.isPresent()).isTrue();
        Optional<User> inDB = repository.findUserByEmailAndPhone(user.getEmail(), user.getPhone());
        assertThat(inDB.isPresent()).isTrue();
        User userInDB = inDB.get();
        assertThat(user.getPhone()).isEqualTo(userInDB.getPhone());
        assertThat(user.getEmail()).isEqualTo(userInDB.getEmail());

    }
}
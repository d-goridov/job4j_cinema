package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.util.UserSession;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    public void whenRegistrationSuccess() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        UserService service = mock(UserService.class);
        when(service.add(user)).thenReturn(Optional.of(user));
        UserController controller = new UserController(service);
        String page = controller.registration(user);
        assertThat(page).isEqualTo("redirect:/success");
    }

    @Test
    public void whenRegistrationError() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        UserService service = mock(UserService.class);
        when(service.add(user)).thenReturn(Optional.empty());
        UserController controller = new UserController(service);
        String page = controller.registration(user);
        assertThat(page).isEqualTo("redirect:/fail");
    }

    @Test
    public void whenFormAddUser() {
        User user = new User(1, "", "", "");
        UserService service = mock(UserService.class);
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        when(UserSession.getUser(session)).thenReturn(user);
        UserController controller = new UserController(service);
        String page = controller.addUser(model, session);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("reg/registration");
    }

    @Test
    public void whenLogout() {
        UserService service = mock(UserService.class);
        HttpSession session = mock(HttpSession.class);
        UserController controller = new UserController(service);
        String page = controller.logout(session);
        assertThat(page).isEqualTo("redirect:/main");
    }

    @Test
    public void whenFail() {
        UserService service = mock(UserService.class);
        Model model = mock(Model.class);
        UserController controller = new UserController(service);
        String page = controller.fail(model);
        verify(model).addAttribute("message", "Пользователь с такой почтой уже существует");
        assertThat(page).isEqualTo("reg/error_reg");
    }

    @Test
    public void whenSuccess() {
        UserService service = mock(UserService.class);
        Model model = mock(Model.class);
        UserController controller = new UserController(service);
        String page = controller.success(model);
        verify(model).addAttribute("message", "Регистрация прошла успешно!");
        assertThat(page).isEqualTo("reg/success_reg");
    }
}
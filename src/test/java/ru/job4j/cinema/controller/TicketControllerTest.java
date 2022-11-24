package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.UserSession;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    @Test
    public void whenGetInfo() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        TicketService service = mock(TicketService.class);
        when(UserSession.getUser(session)).thenReturn(user);
        when(session.getAttribute("sessionId")).thenReturn(2);
        when(session.getAttribute("row")).thenReturn(3);
        when(session.getAttribute("cell")).thenReturn(3);
        TicketController controller = new TicketController(service);
        String page = controller.getInfo(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("sessionId", 2);
        verify(model).addAttribute("row", 3);
        verify(model).addAttribute("cell", 3);
        assertThat(page).isEqualTo("pages/ticket_info");
    }

    @Test
    public void whenCreateTicketError() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        Model model = mock(Model.class);
        TicketService service = mock(TicketService.class);
        HttpSession session = mock(HttpSession.class);
        when(UserSession.getUser(session)).thenReturn(user);
        when(session.getAttribute("sessionId")).thenReturn(1);
        TicketController controller = new TicketController(service);
        String page = controller.error(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("sessionId", 1);
        verify(model).addAttribute("message", "Это место на выбранный фильм уже продано");
        assertThat(page).isEqualTo("pay/error_pay");
    }

    @Test
    public void whenCreateTicketSuccess() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        Model model = mock(Model.class);
        TicketService service = mock(TicketService.class);
        HttpSession session = mock(HttpSession.class);
        when(UserSession.getUser(session)).thenReturn(user);
        when(session.getAttribute("sessionId")).thenReturn(1);
        when(session.getAttribute("row")).thenReturn(3);
        when(session.getAttribute("cell")).thenReturn(3);
        TicketController controller = new TicketController(service);
        String page = controller.success(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("sessionId", 1);
        verify(model).addAttribute("row", 3);
        verify(model).addAttribute("cell", 3);
        assertThat(page).isEqualTo("pay/success_pay");
    }
}
package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.utils.UserSession;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionControllerTest {

    @Test
    void whenShowMain() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        List<Session> sessions = List.of(
                new Session(1, "session_1", null),
                new Session(1, "session_2", null)
        );
        Model model = mock(Model.class);
        SessionService service = mock(SessionService.class);
        HttpSession session = mock(HttpSession.class);
        when(UserSession.getUser(session)).thenReturn(user);
        when(service.findAll()).thenReturn(sessions);
        SessionController sessionController = new SessionController(service);
        String page = sessionController.showMain(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("sessions", sessions);
        assertThat(page).isEqualTo("pages/main");
    }

    @Test
    void whenSelectSession() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        Model model = mock(Model.class);
        SessionService service = mock(SessionService.class);
        HttpSession session = mock(HttpSession.class);
        when(UserSession.getUser(session)).thenReturn(user);
        SessionController sessionController = new SessionController(service);
        String page = sessionController.selectSession(3, model, session);
        verify(session).setAttribute("sessionId", 3);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("sessionId", 3);
        assertThat(page).isEqualTo("pages/select_row");
    }

    @Test
    void whenSaveRow() {
        int row = 3;
        SessionService service = mock(SessionService.class);
        HttpSession session = mock(HttpSession.class);
        SessionController sessionController = new SessionController(service);
        String page = sessionController.saveRow(row, session);
        verify(session).setAttribute("row", row);
        assertThat(page).isEqualTo("redirect:/ticketCell");
    }

    @Test
    void whenSelectCell() {
        User user = new User(1, "Dmitriy", "mail", "111-111");
        Model model = mock(Model.class);
        SessionService service = mock(SessionService.class);
        HttpSession session = mock(HttpSession.class);
        when(UserSession.getUser(session)).thenReturn(user);
        when(session.getAttribute("sessionId")).thenReturn(3);
        SessionController sessionController = new SessionController(service);
        String page = sessionController.selectCell(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("sessionId", 3);
        assertThat(page).isEqualTo("pages/select_cell");
    }

    @Test
    void whenSaveCell() {
        int cell = 3;
        SessionService service = mock(SessionService.class);
        HttpSession session = mock(HttpSession.class);
        SessionController sessionController = new SessionController(service);
        String page = sessionController.saveCell(cell, session);
        verify(session).setAttribute("cell", cell);
        assertThat(page).isEqualTo("redirect:/ticketInfo");
    }

    @Test
    void whenCancel() {
        SessionService service = mock(SessionService.class);
        HttpSession session = mock(HttpSession.class);
        SessionController sessionController = new SessionController(service);
        String page = sessionController.cancel(session);
        assertThat(page).isEqualTo("redirect:/main");
    }
}

package ru.job4j.cinema.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.util.UserSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

/**
 * Контроллер, обрабатывает запросы от пользователя и отправляет ответы
 */
@Controller
public class SessionController {

    /**
     * Объект SessionService для взаимодействия
     * с хранилищем сеансов
     */
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    /**
     * Метод возвращает представление главной страницы сайта
     * со списком сеансов
     * @param model - объект типа Model
     * @param session - объект типа HttpSession
     * @return - представление main
     */
    @GetMapping("/main")
    public String showMain(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("sessions", service.findAll());
        return "pages/main";
    }

    /**
     * Метод преобразует массив байт из файла изображения в строку.
     * Браузер преобразует ее в изображение
     * @param sessionId - идентификатор сеанса
     * @return - объект RepsonseEntity
     */
    @GetMapping("/photoSession/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") Integer sessionId) {
        Resource resource = new ClassPathResource(
                "/image/" + service.findById(sessionId).getName() + ".jpeg"
        );
        byte[] photo = new byte[1028];
        try (InputStream input = resource.getInputStream()) {
            photo = input.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(photo.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(photo));
    }

    /**
     * Метод сохраняет в объект HttpSession идентификатор
     * выбранного сеанса, и возвращает представление
     * для выбора ряда
     * @param sessionId - идентификатор сеанса
     * @param model - объект типа Model
     * @param session - объект типа HttpSession
     * @return - представление select_row
     */
    @GetMapping("/select/{sessionId}")
    public String selectSession(@PathVariable("sessionId") int sessionId, Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        session.setAttribute("sessionId", sessionId);
        model.addAttribute("sessionId", sessionId);
        return "pages/select_row";
    }

    /**
     * Метод сохраняет в HttpSession номер ряда
     * @param row - номер ряда
     * @param session - объект типа HttpSession
     * @return - перенаправляет на адрес с выбором места
     */
    @PostMapping("/ticketRow")
    public String saveRow(@RequestParam("row") int row, HttpSession session) {
        session.setAttribute("row", row);
        return "redirect:/ticketCell";
    }

    /**
     * Метод возвращает представление с выбором места, добавляет в модель
     * объект пользователя и идентификатор сеанса для отображения на
     * возвращаемом представлении
     * @param model - объект типа Model
     * @param session - объект типа HttpSession
     * @return - представление select_cell
     */
    @GetMapping("/ticketCell")
    public String selectCell(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getAttribute("sessionId"));
        return "pages/select_cell";
    }

    /**
     * Метод сохраняет в HttpSession номер выбранного места
     * @param value - номер ряда
     * @param session - объект типа HttpSession
     * @return - перенаправляет на адрес с информацией о билете
     */
    @PostMapping("/selectCell")
    public String saveCell(@RequestParam("cell") int value, HttpSession session) {
        session.setAttribute("cell", value);
        return "redirect:/ticketInfo";
    }

    /**
     * Метод удаляет данные из объекта HttpSession
     * @param session - объект типа HttpSession
     * @return - представление с главной страницей сайта
     */
    @PostMapping("/cancel")
    public String cancel(HttpSession session) {
        session.removeAttribute("sessionId");
        session.removeAttribute("row");
        session.removeAttribute("cell");
        return "redirect:/main";
    }
}

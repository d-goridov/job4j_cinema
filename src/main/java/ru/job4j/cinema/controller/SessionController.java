package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
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

@Controller
@ThreadSafe

public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @GetMapping("/main")
    public String showMain(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("sessions", service.findAll());
        return "pages/main";
    }

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

    @GetMapping("/select/{sessionId}")
    public String selectSession(@PathVariable("sessionId") int sessionId, Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        session.setAttribute("sessionId", sessionId);
        model.addAttribute("sessionId", sessionId);
        return "pages/select_row";
    }

    @PostMapping("/ticketRow")
    public String saveRow(@RequestParam("row") int row, HttpSession session) {
        session.setAttribute("row", row);
        return "redirect:/ticketCell";
    }

    @GetMapping("/ticketCell")
    public String selectCell(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getAttribute("sessionId"));
        return "pages/select_cell";
    }

    @PostMapping("/selectCell")
    public String saveCell(@RequestParam("cell") int value, HttpSession session) {
        session.setAttribute("cell", value);
        return "redirect:/ticketInfo";
    }

    @PostMapping("/cancel")
    public String cancel(HttpSession session) {
        session.removeAttribute("sessionId");
        session.removeAttribute("row");
        session.removeAttribute("cell");
        return "redirect:/main";
    }
}

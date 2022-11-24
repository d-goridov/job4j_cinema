package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.UserSession;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class TicketController {

   final private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping("/ticketInfo")
    public String getInfo(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("row", session.getAttribute("row"));
        model.addAttribute("cell", session.getAttribute("cell"));
        model.addAttribute("sessionId", session.getAttribute("sessionId"));
        return "pages/ticket_info";
    }


    @PostMapping("/createTicket")
    public String create(HttpSession session) {
        Ticket ticket = new Ticket();
        ticket.setSession(new Session((Integer) session.getAttribute("sessionId"), " "));
        ticket.setRow((Integer) session.getAttribute("row"));
        ticket.setCell((Integer) session.getAttribute("cell"));
        ticket.setUser((User) session.getAttribute("user"));
        Optional<Ticket> rsl = ticketService.add(ticket);
        if (rsl.isEmpty()) {
            return "redirect:/errorPay";
        }
        return "redirect:/successPay";
    }

    @GetMapping("/errorPay")
    public String error(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getAttribute("sessionId"));
        model.addAttribute("message", "Это место на выбранный фильм уже продано");
        return "pay/error_pay";
    }

    @GetMapping("/successPay")
    public String success(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("row", session.getAttribute("row"));
        model.addAttribute("cell", session.getAttribute("cell"));
        model.addAttribute("sessionId", session.getAttribute("sessionId"));
        return "pay/success_pay";
    }
}

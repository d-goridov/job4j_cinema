package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import ru.job4j.cinema.util.UserSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Контроллер, обрабатывает запросы от пользователя и отправляет ответы
 */
@Controller
public class UserController {

    /**
     * Объект UserService для взаимодействия
     * с хранилищем пользователей
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод добавляет пользователя в хранилище
     * @param user - объект пользователя
     * @return - перенаправляет запрос на адрес /success, если
     * добавление успешное, иначе на адрес /fail
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    /**
     * Метод возвращает представление с формой добавления нового пользователя
     * @param model объект типа Model
     * @param session Объект типа HttpSession
     * @return представление registration
     */
    @GetMapping("/formAddUser")
    public String addUser(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        return "reg/registration";
    }

    /**
     * Метод возвращает представление с сообщением
     * о неуспешной попытке добавления пользователя
     * @param model - объект типа Model
     * @return - представление error_reg"
     */
    @GetMapping("/fail")
    public String fail(Model model) {
        model.addAttribute("message", "Пользователь с такой почтой уже существует");
        return "reg/error_reg";
    }

    /**
     * Метод возвращает представление с сообщением
     * об успешной попытке добавления пользователя
     * @param model - объект типа Model
     * @return - представление success_reg"
     */
    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("message", "Регистрация прошла успешно!");
        return "reg/success_reg";
    }

    /**
     * Метод возвращает представление с формой для авторизации пользователя
     * @param model Модель с данными
     * @param fail Параметр запроса
     * @return представление login
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "auth/login";
    }

    /**
     * Метод производит поиск пользователя в базе данных по электронной почте и паролю,
     * которые были получены из метода loginPage
     * @param user Объект типа User
     * @param request Объект типа HttpServletRequest
     * @return Если пользователь найден в базе данных произойдет переадресация по адресу /main,
     * иначе - loginPage?fail=true
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/main";
    }

    /**
     * Метод очищает данные из сессии
     * @param session Объект типа HttpSession
     * @return Происходит переадресация на адрес /main
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/main";
    }
}

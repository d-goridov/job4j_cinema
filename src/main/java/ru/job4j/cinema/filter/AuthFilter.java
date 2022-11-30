package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Класс с помощью которого осуществляется доступ к ресурсам перед запросом
 */
@Component
public class AuthFilter implements Filter {

    /**
     * Список строк адресов запросов
     */
    private static final List<String> FILTER =
            List.of("loginPage", "login", "main", "photoSession", "formAddUser", "registration", "fail", "success");

    /**
     * Через этот метод будут проходить запросы к сервлетам.
     * Если запрос идет к адресам "loginPage", "login", "main", "photoSession", "formAddUser", "registration",
     * "fail", "success" то мы их пропускаем.
     * Если запросы идут к другим адресам, то проверяем наличие пользователя в HttpSession
     * Если его нет, то мы переходим на страницу авторизации.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (checkURI(uri)) {
            chain.doFilter(req, res);
            return;
        }

       if (req.getSession().getAttribute("user") == null) {
           res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
       }
        chain.doFilter(req, res);
    }

    /**
     * Проверка адреса на возможность пройти к нему без авторизации
     * @param uri - адрес запроса
     * @return - true, если адрес есть в списке FILTER, иначе - false
     */
    private boolean checkURI(String uri) {
        return AuthFilter.FILTER.stream().anyMatch(uri::contains);
    }
}

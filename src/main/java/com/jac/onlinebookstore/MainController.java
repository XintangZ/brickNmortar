package com.jac.onlinebookstore;

import com.jac.onlinebookstore.book.entity.Book;
import com.jac.onlinebookstore.book.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private BookService bookService;

    @Autowired
    public MainController(BookService theBookService) {
        bookService = theBookService;
    }

    @GetMapping("/")
    public String showHome(HttpServletRequest request, Model model) {
        List<Book> allBooks = bookService.findAll();
        model.addAttribute("books", allBooks);

        model.addAttribute("title", "Home");
        model.addAttribute("view", "home");
        model.addAttribute("currentUrl", request.getRequestURI());

        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("view", "auth/login-form");
        return "auth/auth-layout";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage(Model model) {
        model.addAttribute("view", "error/403");
        return "index";
    }

    @GetMapping("*")
    public String showNotFoundPage(Model model) {
        model.addAttribute("view", "error/404");
        return "index";
    }
}

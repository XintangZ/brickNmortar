package com.jac.onlinebookstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHome() {
        return "redirect:/books/list";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("view", "auth/login-form");
        return "auth/auth-layout";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage(Model model) {
        model.addAllAttributes(Map.of(
                "title", "Access Denied",
                "view", "error/403"
        ));
        return "index";
    }

    @GetMapping("/page-not-found")
    public String showNotFoundPage(Model model) {
        model.addAllAttributes(Map.of(
                "title", "Page Not Found",
                "view", "error/404"
        ));
        return "index";
    }

    @GetMapping("*")
    public String redirectToNotFoundPage() {
        return "redirect:/page-not-found";
    }
}

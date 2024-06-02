package com.jac.onlinebookstore.controller;

import com.jac.onlinebookstore.entity.Book;
import com.jac.onlinebookstore.entity.Request;
import com.jac.onlinebookstore.service.BookService;
import com.jac.onlinebookstore.service.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;
    private final BookService bookService;


    public RequestController(RequestService theRequestService, BookService theBookService) {
        requestService = theRequestService;
        bookService = theBookService;
    }


    @GetMapping("/list")
    public String listRequests(HttpServletRequest request, Model model) {
        List<Request> requests;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            requests = requestService.findAll();
        } else {
            String currentUsername = authentication.getName();
            requests = requestService.findByUsername(currentUsername);
        }

        sortRequests(requests);

        model.addAllAttributes(Map.of(
                "requests", requests,
                "title", "Requests",
                "view", isAdmin ? "requests/list-requests-admin" : "requests/list-requests-user",
                "currentUri", request.getRequestURI()
        ));

        return "index";
    }

    @GetMapping("/confirmation")
    public String showConfirmation(Model model) {
        model.addAllAttributes(Map.of(
                "title", "Confirmation",
                "view", "books/request-confirmation"
        ));

        return "index";
    }

    @PostMapping("/add")
    public String addRequest(@RequestParam int bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Book theBook = bookService.findById(bookId);

        Request theRequest = new Request();
        theRequest.setBookId(bookId);
        theRequest.setBook(theBook);
        theRequest.setUsername(currentUsername);

        requestService.save(theRequest);

        return "redirect:/requests/confirmation";
    }

    @GetMapping("/cancel")
    public String cancelRequest(@RequestParam("requestId") int id) {
        requestService.changeStatusById(id, "cancelled");

        return "redirect:/requests/list";
    }

    @GetMapping("/confirm")
    public String confirmRequest(@RequestParam("requestId") int id) {
        requestService.changeStatusById(id, "confirmed");

        return "redirect:/requests/list";
    }

    private void sortRequests(List<Request> requests) {
        requests.sort((r1, r2) -> {
            // Custom comparator to sort by status, pending requests first
            if (r1.getStatus().equals("pending") && !r2.getStatus().equals("pending")) {
                return -1; // r1 is pending, so it should come before r2
            } else if (!r1.getStatus().equals("pending") && r2.getStatus().equals("pending")) {
                return 1; // r2 is pending, so it should come before r1
            } else {
                // If both are pending or both are not pending, reverse the original order (newest first)
                return Integer.compare(requests.indexOf(r2), requests.indexOf(r1));
            }
        });
    }
}

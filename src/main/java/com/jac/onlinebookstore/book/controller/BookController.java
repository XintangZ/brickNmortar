package com.jac.onlinebookstore.book.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jac.onlinebookstore.book.service.BookService;
import com.jac.onlinebookstore.book.entity.Book;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService theBookService) {
        bookService = theBookService;
    }

    @GetMapping("/list")
    public String listBooks(HttpServletRequest request, Model model) {
        List<Book> allBooks = bookService.findAll();

        model.addAllAttributes(Map.of(
                "books", allBooks,
                "title", "Book Directory",
                "view", "books/list-books",
                "currentUri", request.getRequestURI()
        ));

        return "index";
    }

    @PostMapping("/search")
    public String searchByIsbn(@RequestParam String isbn, Model model, HttpServletRequest request) {
        Book theBook = null;
        String refererUrl = request.getHeader("Referer");
        String refererUri = null;

        try {
            if (refererUrl != null) {
                URI uri = new URI(refererUrl);
                refererUri = uri.getPath();
            }

            theBook = bookService.findByIsbn(isbn);
            model.addAttribute("books", theBook);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        String view = Objects.equals(refererUri, "/") ? "home" : "books/list-books";

        model.addAllAttributes(Map.of(
                "title", "Search Result",
                "isbn", isbn,
                "view", view
        ));

        return "index";
    }

    @GetMapping("/add")
    public String showFormForAdd(Model model) {
        Book newBook = new Book();

        model.addAllAttributes(Map.of(
                "title", "Add Book",
                "book", newBook,
                "view", "books/book-form",
                "action", "Add"
        ));

        return "index";
    }

    @GetMapping("/edit")
    public String showFormForEdit(@RequestParam("bookId") int id, Model model) {
        Book theBook = bookService.findById(id);

        model.addAllAttributes(Map.of(
                "title", "Edit Book",
                "book", theBook,
                "view", "books/book-form",
                "action", "Edit"
        ));

        return "index";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book theBook) {
        bookService.save(theBook);
        return "redirect:/books/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bookId") int id) {
        bookService.deleteById(id);
        return "redirect:/books/list";
    }
}

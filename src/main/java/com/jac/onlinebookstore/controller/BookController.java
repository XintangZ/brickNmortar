package com.jac.onlinebookstore.controller;

import com.jac.onlinebookstore.service.BookService;
import com.jac.onlinebookstore.entity.Book;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService theBookService) {
        bookService = theBookService;
    }

    @GetMapping("/list")
    public String listBooks(HttpServletRequest request, Model model) {
        List<Book> allBooks = bookService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        model.addAllAttributes(Map.of(
                "books", allBooks,
                "title", "Home",
                "view", isAdmin ? "books/list-books-admin" : "books/list-books-user",
                "currentUri", request.getRequestURI()
        ));

        return "index";
    }

    @GetMapping("/search")
    public String searchByIsbn(@RequestParam String isbn, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        try {
            Book theBook = bookService.findByIsbn(isbn);
            model.addAttribute("books", theBook);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAllAttributes(Map.of(
                "title", "Search Result",
                "isbn", isbn,
                "view", isAdmin ? "books/list-books-admin" : "books/list-books-user"
        ));

        return "index";
    }

    @GetMapping("/add")
    public String showFormForAdd(@RequestParam String isbn, Model model) {
        try {
            Book theBook = bookService.findByIsbn(isbn);
            if (theBook != null) {
                return "redirect:/books/edit?bookId=" + theBook.getId();
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        Book newBook = new Book();
        newBook.setIsbn(isbn);

        model.addAllAttributes(Map.of(
                "title", "Add Book",
                "book", newBook,
                "cover", getCoverUrl(isbn),
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
                "cover", getCoverUrl(theBook.getIsbn()),
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

    private String getCoverUrl(String isbn) {
        return "https://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg";
    }
}

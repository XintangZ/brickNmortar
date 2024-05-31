package com.jac.onlinebookstore.book.controller;

import com.jac.onlinebookstore.book.service.BookService;
import com.jac.onlinebookstore.book.entity.Book;
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
    public String listBooks(Model model) {
        List<Book> allBooks = bookService.findAll();

        model.addAllAttributes(Map.of(
                "books", allBooks,
                "title", "Home",
                "view", "home"
        ));

        return "index";
    }

    @GetMapping("/search")
    public String searchByIsbn(@RequestParam String isbn, Model model) {
        try {
            Book theBook = bookService.findByIsbn(isbn);
            model.addAttribute("books", theBook);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAllAttributes(Map.of(
                "title", "Search Result",
                "isbn", isbn,
                "view", "home"
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

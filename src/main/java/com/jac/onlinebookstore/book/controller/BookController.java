package com.jac.onlinebookstore.book.controller;

import com.jac.onlinebookstore.book.service.BookService;
import com.jac.onlinebookstore.book.entity.Book;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        model.addAttribute("books", allBooks);
        model.addAttribute("view", "books/list-books");
        model.addAttribute("currentUrl", request.getRequestURI());
        return "index";
    }

    @GetMapping("/add")
    public String showFormForAdd(Model model) {
        Book newBook = new Book();
        model.addAttribute("book", newBook);
        return "books/book-form";
    }

    @GetMapping("/edit")
    public String showFormForEdit(@RequestParam("bookId") int id, Model model) {
        Book theBook = bookService.findById(id);
        model.addAttribute("book", theBook);
        return "books/book-form";
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

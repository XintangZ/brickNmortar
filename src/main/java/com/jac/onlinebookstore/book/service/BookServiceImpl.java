package com.jac.onlinebookstore.book.service;

import com.jac.onlinebookstore.book.dao.BookRepository;
import com.jac.onlinebookstore.book.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository theBookRepository) {
        bookRepository = theBookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) {
        Optional<Book> result = bookRepository.findByIsbn(isbn);

        Book theBook = null;

        if (result.isPresent()) {
            theBook = result.get();
        }
        else {
            throw new RuntimeException("Could not find book with ISBN \"" + isbn + "\"");
        }

        return theBook;
    }

    @Override
    public Book findById(int id) {
        Optional<Book> result = bookRepository.findById(id);

        Book theBook = null;

        if (result.isPresent()) {
            theBook = result.get();
        }
        else {
            throw new RuntimeException("Could not find book id - " + id);
        }

        return theBook;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }
}

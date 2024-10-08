package com.jac.onlinebookstore.service;

import com.jac.onlinebookstore.dao.BookRepository;
import com.jac.onlinebookstore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

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

        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Could not find book with ISBN \"" + isbn + "\"");
        }
    }

    @Override
    public Book findById(int id) {
        Optional<Book> result = bookRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Could not find book id - " + id);
        }
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

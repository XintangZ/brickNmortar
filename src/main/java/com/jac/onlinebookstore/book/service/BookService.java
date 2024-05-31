package com.jac.onlinebookstore.book.service;

import com.jac.onlinebookstore.book.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByIsbn(String isbn);

    Book findById(int id);

    void save(Book book);

    void deleteById(int id);
}

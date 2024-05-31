package com.jac.onlinebookstore.service;

import com.jac.onlinebookstore.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByIsbn(String isbn);

    Book findById(int id);

    void save(Book book);

    void deleteById(int id);
}

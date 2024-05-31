package com.jac.onlinebookstore.book.dao;

import com.jac.onlinebookstore.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    public Optional<Book> findByIsbn(String isbn);
}

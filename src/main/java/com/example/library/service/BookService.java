package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    void saveBook(Book book);
    void deleteBook(Long id);
    List<Book> searchBooks(String keyword);

    // ✅ Add this
    List<Book> getAvailableBooks();
}

package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.IssuedBook;
import com.example.library.model.User;

import java.util.List;

public interface IssuedBookService {
//    IssuedBook issueBook(Book book, User student, int issueDays);
    void returnBook(Long issuedBookId);
    List<IssuedBook> getAllIssuedBooks();
    List<IssuedBook> getIssuedBooksByStudent(User student);
    long calculateFine(IssuedBook issuedBook);
    void issueBook(Long bookId, Long studentId, int days);
    List<IssuedBook> findByStudentId(Long studentId);
    void markAsReturned(Long issuedBookId);

}

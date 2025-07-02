package com.example.library.service.Impl;

import com.example.library.model.Book;
import com.example.library.model.IssuedBook;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.IssuedBookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.IssuedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IssuedBookServiceImpl implements IssuedBookService {

    @Autowired
    private IssuedBookRepository issuedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void issueBook(Long bookId, Long studentId, int days) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setBook(book);
        issuedBook.setStudent(student);
        issuedBook.setIssueDate(LocalDate.now());
        issuedBook.setDueDate(LocalDate.now().plusDays(days));
        issuedBook.setReturned(false);

        issuedBookRepository.save(issuedBook);
    }

    @Override
    public void returnBook(Long issuedBookId) {
        IssuedBook issuedBook = issuedBookRepository.findById(issuedBookId)
                .orElseThrow(() -> new RuntimeException("Issued book not found"));

        issuedBook.setReturned(true);
        issuedBookRepository.save(issuedBook);
    }

    @Override
    public List<IssuedBook> getAllIssuedBooks() {
        return issuedBookRepository.findAll();
    }

    @Override
    public List<IssuedBook> getIssuedBooksByStudent(User student) {
        return issuedBookRepository.findByStudent(student);
    }

    @Override
    public List<IssuedBook> findByStudentId(Long studentId) {
        return issuedBookRepository.findByStudentId(studentId);
    }

    @Override
    public long calculateFine(IssuedBook issuedBook) {
        if (issuedBook.isReturned()) {
            return 0;
        }

        LocalDate today = LocalDate.now();
        LocalDate dueDate = issuedBook.getDueDate();

        if (today.isAfter(dueDate)) {
            long overdueDays = ChronoUnit.DAYS.between(dueDate, today);
            return overdueDays * 10; // ₹10 fine per day
        } else {
            return 0;
        }
    }
    @Override
    public void markAsReturned(Long issuedBookId) {
        IssuedBook issuedBook = issuedBookRepository.findById(issuedBookId)
                .orElseThrow(() -> new RuntimeException("Issued book not found"));

        if (issuedBook.getReturnDate() == null) {
            issuedBook.setReturnDate(LocalDate.now());
            issuedBook.setReturned(true); // Optional if you're tracking both
            issuedBookRepository.save(issuedBook);
        }
    }

}

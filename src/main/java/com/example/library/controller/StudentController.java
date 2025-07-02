package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.IssuedBook;
import com.example.library.model.User;
import com.example.library.service.BookService;
import com.example.library.service.IssuedBookService;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final BookService bookService;
    private final UserService userService;
    private final IssuedBookService issuedBookService;

    // ✅ Dashboard view
    @GetMapping("/dashboard")
    public String studentDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User student = userService.getUserByUsername(username);
        model.addAttribute("student", student);
        return "student_dashboard";
    }

    // ✅ View available books
    @GetMapping("/books")
    public String viewAvailableBooks(Model model) {
        List<Book> availableBooks = bookService.getAvailableBooks();
        model.addAttribute("books", availableBooks);
        return "student_books";
    }

    // ✅ Issue book (default 7 days)
    @PostMapping("/issue/{bookId}")
    public String issueBook(@PathVariable Long bookId, Authentication authentication) {
        String username = authentication.getName();
        User student = userService.getUserByUsername(username);
        issuedBookService.issueBook(bookId, student.getId(), 7); // issue for 7 days
        return "redirect:/student/books";
    }

    // ✅ View issued books
    @GetMapping("/issued")
    public String viewIssuedBooks(Model model, Authentication authentication) {
        String username = authentication.getName();
        User student = userService.getUserByUsername(username);
        List<IssuedBook> issuedBooks = issuedBookService.findByStudentId(student.getId());
        model.addAttribute("issuedBooks", issuedBooks);
        return "student_issued_books";
    }

    // ✅ Return book
    @GetMapping("/return/{issueId}")
    public String returnBook(@PathVariable Long issueId) {
        issuedBookService.returnBook(issueId);
        return "redirect:/student/issued";
    }
}

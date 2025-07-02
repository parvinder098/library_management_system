package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookService;
import com.example.library.service.UserService;
import com.example.library.service.IssuedBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;
    private final UserService userService;
    private final IssuedBookService issuedBookService;

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin_dashboard";
    }

    // View all books
    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "view_books";
    }

    // Add new book form
    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add_book";
    }

    // Save new book
    @PostMapping("/books/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/admin/books";
    }

    // Delete book
    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }

    // View all registered students
    @GetMapping("/students")
    public String viewStudents(Model model) {
        model.addAttribute("students", userService.getAllStudents());  // ✅ Only students
        return "view_students";
    }

    // View all issued books
    @GetMapping("/issued")
    public String issuedBooks(Model model) {
        model.addAttribute("issuedBooks", issuedBookService.getAllIssuedBooks());
        return "issued_books_admin";
    }

    // Show form to issue a book to student
    @GetMapping("/issue-book")
    public String showIssueBookForm(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("students", userService.getAllStudents());
        return "issue_book_form";
    }

    // Handle form submission to issue book
    @PostMapping("/issue-book")
    public String handleIssueBook(@RequestParam Long bookId,
                                  @RequestParam Long studentId,
                                  @RequestParam int days) {
        issuedBookService.issueBook(bookId, studentId, days);
        return "redirect:/admin/issued";
    }
}

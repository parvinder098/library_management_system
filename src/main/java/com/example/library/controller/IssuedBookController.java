package com.example.library.controller;

import com.example.library.model.IssuedBook;
import com.example.library.model.User;
import com.example.library.service.IssuedBookService;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IssuedBookController {

    private final IssuedBookService issuedBookService;
    private final UserService userService;

    // ✅ Admin View: All Issued Books
    @GetMapping("/admin/issued-books")
    public String viewAllIssuedBooks(Model model) {
        List<IssuedBook> issuedBooks = issuedBookService.getAllIssuedBooks();
        model.addAttribute("issuedBooks", issuedBooks);
        return "issued_books_admin";
    }
    @GetMapping("/admin/issued/return/{id}")
    public String markAsReturned(@PathVariable("id") Long issuedBookId) {
        issuedBookService.markAsReturned(issuedBookId);
        return "redirect:/admin/issued-books";
    }




    // ✅ Student View: Books issued to current logged-in student
    @GetMapping("/student/my-issued-books")
    public String viewMyIssuedBooks(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Get logged-in username

        User student = userService.findByUsername(username); // Get full User object
        List<IssuedBook> issuedBooks = issuedBookService.getIssuedBooksByStudent(student); // Fetch student's issued books

        model.addAttribute("issuedBooks", issuedBooks);
        return "issued_books_student"; // HTML file to be created
    }


}

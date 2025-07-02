package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }
//@GetMapping("/admin/books/add")
//public String showAddBookForm(Model model) {
//    model.addAttribute("book", new Book());
//    return "add_book";
//}


    @PostMapping("/admin/books/add")
    public String addBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("success", "Book added successfully!");
        return "redirect:/admin/books/add";
    }

    // 📚 List all books (with optional search)
    @GetMapping
    public String listBooks(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("books", bookService.searchBooks(keyword));
        model.addAttribute("keyword", keyword);
        return "index";
    }

    // Show book creation form (Admin only)
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "create_book";
    }

    //  Edit existing book
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "create_book";
    }

    // Save new or edited book
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    // Delete book
//    @GetMapping("/admin/books/delete/{id}")
//    public String deleteBook(@PathVariable Long id) {
//        bookService.deleteBook(id);
//        return "redirect:/admin/books";
//    }

//    @GetMapping("/admin/books")
//    public String viewBooks(Model model) {
//        List<Book> books = bookService.getAllBooks();
//        model.addAttribute("books", books);
//        return "view_books";
//    }




}

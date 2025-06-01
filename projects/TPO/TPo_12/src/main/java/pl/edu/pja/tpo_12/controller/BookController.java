package pl.edu.pja.tpo_12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pja.tpo_12.model.Book;
import pl.edu.pja.tpo_12.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("pageTitle", "Browse Books");
        return "books/list";
    }

    @GetMapping("/{id}")
    public String viewBook(Model model) {
        model.addAttribute("pageTitle", "Book Details");
        return "books/view";
    }
}
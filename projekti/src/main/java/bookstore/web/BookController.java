package bookstore.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;
import bookstore.domain.CategoryRepository;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookRepository repository;
    private final CategoryRepository cRepository;

    public BookController(BookRepository repository, CategoryRepository cRepository) {
        this.repository = repository;
        this.cRepository = cRepository;
    }

    @RequestMapping(value="/login")
    public String login() {
        return "booklist";
    }

    @GetMapping(value = { "/", "/booklist" })
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", cRepository.findAll());
        return "addbook";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        log.info("CONTROLLER: Save the book - check validation of book: " + book);
        if (bindingResult.hasErrors()) {
            log.info("some validation error happened, book: " + book);
            model.addAttribute("book", book);
            model.addAttribute("categories", cRepository.findAll());
            return "addbook";
        }
        repository.save(book);
        return "redirect:/booklist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/edit/{id}")
    public String showEditBook(@PathVariable("id") Long bookId, Model model) {
        Book book = repository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + bookId));
        model.addAttribute("book", book);
        model.addAttribute("categories", cRepository.findAll()); 
        return "editbook";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book) {
        repository.save(book);
        return "redirect:/booklist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method=RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        repository.deleteById(bookId);
        return "redirect:/booklist";
    }
    
}
package bookstore.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bookstore.domain.Book;
import bookstore.domain.BookRepository;
import bookstore.domain.CategoryRepository;
import jakarta.validation.Valid;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookRepository repository;
    private final CategoryRepository cRepository;

    public BookController(BookRepository repository, CategoryRepository cRepository) {
        this.repository = repository;
        this.cRepository = cRepository;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "booklist"; // Assuming you have a login page to redirect to.
    }

    // Displays the list of books
    @GetMapping(value = { "/", "/booklist" })
    public String bookList(Model model) {
        model.addAttribute("books", repository.findAll());
        return "booklist";
    }

    // Admin: Displays the form for adding a new book
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", cRepository.findAll());
        return "addbook";
    }

    // Admin: Saves a new book, with validation
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        log.info("CONTROLLER: Saving the book: " + book);

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors occurred while saving the book: " + book);
            model.addAttribute("categories", cRepository.findAll());
            return "addbook"; // Return the form with errors
        }

        // Check for ISBN duplication before saving
        if (repository.existsByIsbn(book.getIsbn())) {
            bindingResult.rejectValue("isbn", "isbn.duplicate", "ISBN already exists.");
            model.addAttribute("categories", cRepository.findAll());
            return "addbook";
        }

        repository.save(book);
        log.info("Book saved successfully: " + book);
        return "redirect:/booklist"; // Redirect to book list after saving
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/edit/{id}")
    public String showEditBook(@PathVariable("id") Long bookId, Model model) {
    Book book = repository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + bookId));
    model.addAttribute("book", book);
    model.addAttribute("categories", cRepository.findAll());
    return "editbook";
}

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/updateBook")
    public String updateBook(@Valid @ModelAttribute Book book, BindingResult bindingResult, Model model) {
        log.info("CONTROLLER: Updating the book: " + book);

    // Check for validation errors
    if (bindingResult.hasErrors()) {
        log.warn("Validation errors occurred while updating the book: " + book);
        model.addAttribute("categories", cRepository.findAll());
        return "editbook"; // Return the form with errors
    }

    // Check for ISBN duplication before saving
    // If the ISBN is being changed, check for duplication
    Book existingBook = repository.findById(book.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
    
    if (!existingBook.getIsbn().equals(book.getIsbn()) && repository.existsByIsbn(book.getIsbn())) {
        bindingResult.rejectValue("isbn", "isbn.duplicate", "ISBN already exists.");
        model.addAttribute("categories", cRepository.findAll());
        return "editbook"; // Return the form with the ISBN error
    }

    // Save the book if no validation errors
    repository.save(book);
    log.info("Book updated successfully: " + book);

    // Redirect to the book list after updating
    return "redirect:/booklist";
}
    // Admin: Deletes a book
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId) {
        repository.deleteById(bookId);
        log.info("Deleted book with ID: " + bookId);
        return "redirect:/booklist"; // Redirect to book list after deletion
    }
}

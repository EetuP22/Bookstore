package bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;


@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/index")
    public String showIndex(Model model) {
        model.addAttribute("message", "Welcome to the Bookstore!");
        return "index";
    }

    @GetMapping("/booklist")
    public String bookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    @GetMapping("/delete{id}")
    public String deleteBook (@PathVariable("id") Long bookId) {
        bookRepository.deleteById(bookId);
        return "redirect:../booklist";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @GetMapping("/save") 
    public String save(Book book) {
        bookRepository.save(book);
        return "redirect:../booklist";
    }
}

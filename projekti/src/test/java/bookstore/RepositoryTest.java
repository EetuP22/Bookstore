package bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;
import bookstore.domain.Category;
import bookstore.domain.CategoryRepository;
import bookstore.domain.AppUser;
import bookstore.domain.AppUserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RepositoryTest {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Test
    public void testAppUserRepository() {
        AppUser user = new AppUser("testuser123", "passwordHash", "user");
        appUserRepository.save(user);

        AppUser found = appUserRepository.findByUsername(user.getUsername());
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(user.getUsername());

        appUserRepository.delete(found);
        Optional<AppUser> deleted = appUserRepository.findById(found.getId());
        assertThat(deleted).isEmpty();
    }
    
    @Test
    public void testBookRepository() {
        Category category = new Category("Test Fiction");
        categoryRepository.save(category);

        Book book = new Book(
            "Test Book Title", 
            "Test Author", 
            2000, 
            "1234567890123",  
            new BigDecimal("10.99"), 
            category
        );
        bookRepository.save(book);

        List<Book> foundBooks = bookRepository.findByTitleContainingIgnoreCase("test");
        assertThat(foundBooks).hasSize(1);
        assertThat(foundBooks.get(0).getTitle()).isEqualTo(book.getTitle());

        bookRepository.delete(book);
        Optional<Book> deleted = bookRepository.findById(book.getId());
        assertThat(deleted).isEmpty();
    }
    
    @Test
    public void testCategoryRepository() {
        Category category = new Category("Test Science Fiction");
        categoryRepository.save(category);

        Optional<Category> found = categoryRepository.findById(category.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(category.getName());

        categoryRepository.delete(category);
        Optional<Category> deleted = categoryRepository.findById(category.getId());
        assertThat(deleted).isEmpty();
    }
}

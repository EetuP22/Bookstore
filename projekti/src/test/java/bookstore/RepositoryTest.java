package bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@EnableAutoConfiguration
public class RepositoryTest {

    @Autowired private BookRepository bookRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private AppUserRepository appUserRepository;

    @Configuration
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    
    @Test
    public void testAppUserRepository() {
        
        AppUser user = new AppUser("testuser", "passwordHash", "ROLE_USER");
        appUserRepository.save(user);

        
        AppUser found = appUserRepository.findByUsername(user.getUsername());
        assertThat(found.getUsername()).isEqualTo(user.getUsername());

        
        appUserRepository.delete(found);
        Optional<AppUser> deleted = appUserRepository.findById(found.getId());
        assertThat(deleted).isEmpty();
    }

    // Test BookRepository
    @Test
    public void testBookRepository() {
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", new BigDecimal("10.99"), category);
        bookRepository.save(book);

        List<Book> foundBooks = bookRepository.findByTitleContainingIgnoreCase("great");
        assertThat(foundBooks).hasSize(1);
        assertThat(foundBooks.get(0).getTitle()).isEqualTo(book.getTitle());

        bookRepository.delete(book);
        Optional<Book> deleted = bookRepository.findById(book.getId());
        assertThat(deleted).isEmpty();
    }


    @Test
    public void testCategoryRepository() {
        // Create
        Category category = new Category("Science Fiction");
        categoryRepository.save(category);

        // Search
        Optional<Category> found = categoryRepository.findById(category.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(category.getName());

        // Delete
        categoryRepository.delete(category);
        Optional<Category> deleted = categoryRepository.findById(category.getId());
        assertThat(deleted).isEmpty();
    }
}
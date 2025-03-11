package bookstore;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;
import bookstore.domain.Category;
import bookstore.domain.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {ProjektiApplication.class})
@AutoConfigureMockMvc
@Transactional
public class BookRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Category getOrCreateCategory(String name) {
        return categoryRepository.findByName(name)
                .orElseGet(() -> categoryRepository.save(new Category(name)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void testGetBookById() throws Exception {
        Category category = getOrCreateCategory("Fiction");

        Book book = new Book("New Book", "New Author", 2022, "1234567890123", new BigDecimal("29.99"), category);
        book = bookRepository.save(book);

        mockMvc.perform(get("/api/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void testAddBook() throws Exception {
        Category category = getOrCreateCategory("Fiction");

        Book book = new Book("New Book", "New Author", 2022, "1234567890123", new BigDecimal("29.99"), category);

        mockMvc.perform(post("/api/books")
                        .with(csrf()) 
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void testDeleteBook() throws Exception {
        Category category = getOrCreateCategory("Fiction");

        Book book = new Book("Delete Book", "Delete Author", 2020, "1234567890123", new BigDecimal("9.99"), category);
        book = bookRepository.save(book);

        mockMvc.perform(delete("/api/books/" + book.getId())
                        .with(csrf())) 
                .andExpect(status().isNoContent());
    }
}

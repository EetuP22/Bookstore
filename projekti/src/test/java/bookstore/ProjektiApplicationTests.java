package bookstore;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import bookstore.domain.BookRepository;
import bookstore.domain.CategoryRepository;
import bookstore.domain.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = ProjektiApplication.class)
class ProjektiApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testDatabaseConnection() {
		assertThat(bookRepository).isNotNull();
		assertThat(categoryRepository).isNotNull();
		assertThat(appUserRepository).isNotNull();
	}
}
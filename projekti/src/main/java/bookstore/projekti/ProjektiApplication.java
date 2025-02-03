package bookstore.projekti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;

@SpringBootApplication
@EntityScan(basePackages = {"bookstore.domain"})
@EnableJpaRepositories(basePackages = {"bookstore.domain"})
public class ProjektiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjektiApplication.class, args);
	}

	@Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            repository.save(new Book("The Book Of Five Rings", "Miyamoto Musashi", 1645, "978-1590309841", 9.99));
            repository.save(new Book("1984", "George Orwell", 1949, "978-0451524935", 12.99));
            repository.save(new Book("Meditations", "Marcus Aurelius", 180, "978-0812968255", 7.99));

            
        };
    }
}

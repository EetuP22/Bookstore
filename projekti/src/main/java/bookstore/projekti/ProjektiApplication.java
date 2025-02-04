package bookstore.projekti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;

@SpringBootApplication(scanBasePackages = "bookstore")
@EntityScan("bookstore.domain")
@EnableJpaRepositories("bookstore.domain")
public class ProjektiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektiApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            repository.save(new Book("The Book Of Five Rings", "Miyamoto Musashi", 1645, "9781590309841", 9.99));
            repository.save(new Book("Meditations", "Marcus Aurelius", 180, "9780140449334", 12.99));
            repository.save(new Book("Metamorphosis", "Franz Kafka", 1915, "9780140283295", 7.99));
        };
    }
}
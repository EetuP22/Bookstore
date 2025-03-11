package bookstore;

// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// import bookstore.domain.AppUser;
// import bookstore.domain.AppUserRepository;
// import bookstore.domain.Book;
// import bookstore.domain.BookRepository;
// import bookstore.domain.Category;
// import bookstore.domain.CategoryRepository;

// import java.math.BigDecimal;

@SpringBootApplication(scanBasePackages = "bookstore")
@EntityScan("bookstore.domain")
@EnableJpaRepositories("bookstore.domain")
@ComponentScan(basePackages = {"bookstore"})
public class ProjektiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektiApplication.class, args);
    }

    
    

    // @Bean
    // public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository, AppUserRepository uRepository, PasswordEncoder passwordEncoder) {
    //     return (args) -> {

    //         Category fiction = new Category("Fiction");
    //         Category philosophy = new Category("Philosophy");
    //         Category history = new Category("History");

    //         categoryRepository.save(fiction);
    //         categoryRepository.save(philosophy);
    //         categoryRepository.save(history);

    //         bookRepository.save(new Book("The Book Of Five Rings", "Miyamoto Musashi", 1645, "9781590309841", new BigDecimal("9.99"), philosophy));
    //         bookRepository.save(new Book("Meditations", "Marcus Aurelius", 180, "9780140449334", new BigDecimal("12.99"), philosophy));
    //         bookRepository.save(new Book("Metamorphosis", "Franz Kafka", 1915, "9780140283295", new BigDecimal("7.99"), fiction));

            
        //     AppUser user1 = new AppUser("user", "$2a$10$4XXGNu904amNx2Q0tPL4OOZiLP012ULGVI2a8Em4SAJGCtQG1WBJu", "USER"); //salasana: user
		// 	AppUser user2 = new AppUser("admin", "$2a$10$YAvqdDch33w5BecjHl6WP.LjpNaYy9jQlTcE.Krl3ib7VvzRiKUta", "ADMIN"); //salasana: admin
		// 	uRepository.save(user1);
		// 	uRepository.save(user2);
        // };
    
}
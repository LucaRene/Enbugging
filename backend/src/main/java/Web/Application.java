package Web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

/**
 * Main entry point for the Spring Boot application.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(Application.class, args);
    }
}
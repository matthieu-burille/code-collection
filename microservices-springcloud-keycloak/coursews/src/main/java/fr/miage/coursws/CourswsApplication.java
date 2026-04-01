package fr.miage.coursws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class CourswsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourswsApplication.class, args);
    }

}

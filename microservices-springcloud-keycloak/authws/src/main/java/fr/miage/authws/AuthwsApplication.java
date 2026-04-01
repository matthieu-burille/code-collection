package fr.miage.authws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class AuthwsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthwsApplication.class, args);
    }

}

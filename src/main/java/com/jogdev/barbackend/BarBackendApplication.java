package com.jogdev.barbackend;

import com.jogdev.barbackend.bar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class BarBackendApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BarBackendApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        String password = passwordEncoder.encode("password");
        System.out.printf("Password: %s%n", password);


    }
}

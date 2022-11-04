package ru.practicum.ewmService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan("ru.practicum")
public class ExploreWithMeService {
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeService.class, args);
    }
}

package ru.practicum.ewmServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.practicum.ewmServer", "ru.practicum.statClient"})
public class ExploreWithMeService {
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeService.class, args);
    }
}

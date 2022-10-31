package ru.practicum.ewmServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmServer.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

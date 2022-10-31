package ru.practicum.ewmServer.model.dto.mapper;

import ru.practicum.ewmServer.model.Category;
import ru.practicum.ewmServer.model.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}

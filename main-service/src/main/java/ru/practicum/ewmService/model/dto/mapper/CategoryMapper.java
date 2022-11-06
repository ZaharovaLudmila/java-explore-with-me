package ru.practicum.ewmService.model.dto.mapper;

import ru.practicum.ewmService.model.Category;
import ru.practicum.ewmService.model.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}

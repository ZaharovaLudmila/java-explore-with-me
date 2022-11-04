package ru.practicum.ewm.model.dto.mapper;

import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}

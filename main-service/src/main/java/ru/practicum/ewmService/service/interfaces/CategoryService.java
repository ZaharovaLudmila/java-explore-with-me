package ru.practicum.ewmService.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewmService.model.dto.CategoryDto;
import ru.practicum.ewmService.model.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto adminUpdateCategory(CategoryDto categoryDto);

    CategoryDto adminAddCategory(NewCategoryDto categoryDto);

    void adminDeleteCategory(long catId);

    List<CategoryDto> publicFindCategory(PageRequest of);

    CategoryDto publicFindCategoryById(long catId);
}

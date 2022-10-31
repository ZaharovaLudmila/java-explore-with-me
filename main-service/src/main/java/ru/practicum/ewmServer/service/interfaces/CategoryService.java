package ru.practicum.ewmServer.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewmServer.model.dto.CategoryDto;
import ru.practicum.ewmServer.model.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto adminUpdateCategory(CategoryDto categoryDto);

    CategoryDto adminAddCategory(NewCategoryDto categoryDto);

    void adminDeleteCategory(long catId);

    List<CategoryDto> publicFindCategory(PageRequest of);

    CategoryDto publicFindCategoryById(long catId);
}

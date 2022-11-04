package ru.practicum.ewm.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.model.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto adminUpdateCategory(CategoryDto categoryDto);

    CategoryDto adminAddCategory(NewCategoryDto categoryDto);

    void adminDeleteCategory(long catId);

    List<CategoryDto> publicFindCategory(PageRequest of);

    CategoryDto publicFindCategoryById(long catId);
}

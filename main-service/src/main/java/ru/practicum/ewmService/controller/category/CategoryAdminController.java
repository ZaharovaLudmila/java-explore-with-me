package ru.practicum.ewmService.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmService.model.dto.CategoryDto;
import ru.practicum.ewmService.model.dto.NewCategoryDto;
import ru.practicum.ewmService.service.interfaces.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@Validated
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PatchMapping()
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {

        log.info("Admin - обновление категории: {}, id {}", categoryDto.getName(), categoryDto.getId());
        return categoryService.adminUpdateCategory(categoryDto);
    }

    @PostMapping()
    public CategoryDto addCategory(@Valid @RequestBody NewCategoryDto categoryDto) {

        log.info("Admin - добавление новой категории: {}", categoryDto.getName());
        return categoryService.adminAddCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@Positive @PathVariable("catId") long catId) {
        log.info("Admin - удаление категории с id: {}", catId);
        categoryService.adminDeleteCategory(catId);
    }
}

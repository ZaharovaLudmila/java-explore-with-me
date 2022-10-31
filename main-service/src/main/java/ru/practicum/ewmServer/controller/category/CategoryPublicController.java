package ru.practicum.ewmServer.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmServer.model.dto.CategoryDto;
import ru.practicum.ewmServer.service.interfaces.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/categories")
@Slf4j
@Validated
public class CategoryPublicController {
    private final CategoryService categoryService;

    @GetMapping()
    public List<CategoryDto> findCategory(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                          Integer from,
                                          @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("Public - получение списка категорий");
        return categoryService.publicFindCategory(PageRequest.of(from / size, size));
    }

    @GetMapping("{catId}")
    public CategoryDto findCategoryById(@PathVariable long catId) {

        log.info("Public - получение категории по id {}", catId);
        return categoryService.publicFindCategoryById(catId);
    }
}

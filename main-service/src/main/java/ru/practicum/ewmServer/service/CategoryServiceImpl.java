package ru.practicum.ewmServer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmServer.exception.ConditionException;
import ru.practicum.ewmServer.exception.NotFoundException;
import ru.practicum.ewmServer.model.Category;
import ru.practicum.ewmServer.model.dto.CategoryDto;
import ru.practicum.ewmServer.model.dto.NewCategoryDto;
import ru.practicum.ewmServer.model.dto.mapper.CategoryMapper;
import ru.practicum.ewmServer.repository.CategoryRepository;
import ru.practicum.ewmServer.repository.EventRepository;
import ru.practicum.ewmServer.service.interfaces.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CategoryDto adminUpdateCategory(CategoryDto categoryDto) {
        Category oldCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() ->
                new NotFoundException(String.format("Category with id=%d was not found.", categoryDto.getId())));
        if (oldCategory != null) {
            oldCategory.setName(categoryDto.getName());
            return CategoryMapper.toCategoryDto(categoryRepository.save(oldCategory));
        } else {
            throw new NotFoundException(String.format("Category with id=%d was not found.", categoryDto.getId()));
        }
    }

    @Transactional
    @Override
    public CategoryDto adminAddCategory(NewCategoryDto categoryDto) {
        Category category = categoryRepository.save(new Category(0L, categoryDto.getName()));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void adminDeleteCategory(long catId) {
        if (eventRepository.findAllByCategoryId(catId) > 0 ) {
            throw new ConditionException("Only categories not related to events can be deleted");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> publicFindCategory(PageRequest of) {
        return categoryRepository.findAll(of).stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto publicFindCategoryById(long catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id=%d was not found.", catId))));
    }
}

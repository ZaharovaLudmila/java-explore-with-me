package ru.practicum.ewmService.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewmService.model.dto.NewUserRequest;
import ru.practicum.ewmService.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(PageRequest pageable);

    List<UserDto> findByIds(List<Long> ids, PageRequest pageable);

    UserDto saveUser(NewUserRequest newUser);

    void deleteUser(long userId);
}

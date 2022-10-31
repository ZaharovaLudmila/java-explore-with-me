package ru.practicum.ewmServer.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmServer.model.dto.NewUserRequest;
import ru.practicum.ewmServer.model.dto.UserDto;
import ru.practicum.ewmServer.service.interfaces.UserService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@Validated
public class UserAdminController {
    private final UserService userService;

    @GetMapping()
    public List<UserDto> findAll(@RequestParam(value = "ids", required = false) List<Long> ids,
                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                 @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        if (ids == null) {
            log.info("Admin - получение пользователей");
            return userService.findAll(pageable);
        } else {
            log.info("Admin - получение пользователей по id {}", ids);
            return userService.findByIds(ids, pageable);
        }
    }

    @PostMapping()
    public UserDto addUser(@Validated @RequestBody NewUserRequest newUser) {

        log.info("Admin - добавление нового пользователя: {}", newUser.getName());
        return userService.saveUser(newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteCategory(@Positive @PathVariable("userId") long userId) {
        log.info("Admin - удаление пользователя с id: {}", userId);
        userService.deleteUser(userId);
    }
}

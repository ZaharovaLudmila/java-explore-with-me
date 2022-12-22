package ru.practicum.ewmService.model.dto.mapper;

import ru.practicum.ewmService.model.User;
import ru.practicum.ewmService.model.dto.UserDto;
import ru.practicum.ewmService.model.dto.UserShortDto;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName(), user.getRate() != null
                ? user.getRate().toString() + " %" : "нет отзывов");
    }
}

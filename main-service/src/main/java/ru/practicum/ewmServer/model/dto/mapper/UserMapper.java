package ru.practicum.ewmServer.model.dto.mapper;

import ru.practicum.ewmServer.model.User;
import ru.practicum.ewmServer.model.dto.UserDto;
import ru.practicum.ewmServer.model.dto.UserShortDto;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}

package ru.practicum.ewmServer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmServer.model.User;
import ru.practicum.ewmServer.model.dto.NewUserRequest;
import ru.practicum.ewmServer.model.dto.UserDto;
import ru.practicum.ewmServer.model.dto.mapper.UserMapper;
import ru.practicum.ewmServer.repository.UserRepository;
import ru.practicum.ewmServer.service.interfaces.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public List<UserDto> findAll(PageRequest pageable) {
        return userRepository.findAll(pageable).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<UserDto> findByIds(List<Long> ids, PageRequest pageable) {
        return userRepository.findByIds(ids, pageable).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto saveUser(NewUserRequest newUser) {
        return UserMapper.toUserDto(userRepository.save(
                new User(0L, newUser.getName(), newUser.getEmail())));
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}

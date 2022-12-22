package ru.practicum.ewmService.service.interfaces;

public interface LikeService {

    void userAddLike(long userId, long eventId, Boolean isPositive);

    void userDeleteLike(long userId, long eventId);
}

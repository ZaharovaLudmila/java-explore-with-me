package ru.practicum.ewmService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmService.exception.ActionForbiddenException;
import ru.practicum.ewmService.exception.ConditionException;
import ru.practicum.ewmService.exception.NotFoundException;
import ru.practicum.ewmService.model.Event;
import ru.practicum.ewmService.model.Like;
import ru.practicum.ewmService.model.StatusRequest;
import ru.practicum.ewmService.model.User;
import ru.practicum.ewmService.repository.EventRepository;
import ru.practicum.ewmService.repository.LikeRepository;
import ru.practicum.ewmService.repository.RequestRepository;
import ru.practicum.ewmService.repository.UserRepository;
import ru.practicum.ewmService.service.interfaces.LikeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Transactional
    @Override
    public void userAddLike(long userId, long eventId, Boolean isPositive) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id = %d was not found.", userId)));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));
        if (event.getInitiator().getId() == userId) {
            throw new ActionForbiddenException("The initiator of an event cannot set like/dislike his event.");
        }

        if (!requestRepository.existsByRequesterIdAndEventIdAndStatus(userId, eventId, StatusRequest.CONFIRMED)) {
            throw new ActionForbiddenException(
                    "It is forbidden to give like/dislike to events in which you do not participate.");
        }
        Optional<Like> likeOptional = likeRepository.findLikeByEventIdAndUserId(eventId, userId);
        Like like;
        if (likeOptional.isPresent()) {
            if (!likeOptional.get().getIsPositive().equals(isPositive)) {
                like = likeOptional.get();
                like.setIsPositive(isPositive);
            } else {
                throw new ConditionException(
                        String.format("Like / dislike user with id: %d to event with id:%d is already exists.",
                        userId, eventId));
            }
        } else {
            like = new Like(0L, event, user, isPositive);
        }
        likeRepository.save(like);
        log.info("Private - добавлен {} событию с id {} пользователем с id {}",
                isPositive ? "like" : "dislike", eventId, userId);

        updateEventRate(event);
        updateUserRate(event.getInitiator());
    }

    @Transactional
    @Override
    public void userDeleteLike(long userId, long eventId) {
        checkUser(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));
        Like like = likeRepository.findLikeByEventIdAndUserId(eventId, userId).orElseThrow(() ->
                new NotFoundException(String.format("Like with user id = %d and event id = %d was not found.",
                        userId, eventId)));
        likeRepository.delete(like);
        log.info("Private - удален {} события с id {} пользователем с id {}",
                like.getIsPositive() ? "like" : "dislike", eventId, userId);
        updateEventRate(event);
        updateUserRate(event.getInitiator());
    }

    private void updateEventRate(Event event) {
        Double rate = calculateRate(event.getId());
        event.setRate(rate);
        eventRepository.save(event);
        log.info("Private - обновлен рейтинг события с id {}", event.getId());
    }

    private void updateUserRate(User user) {
        Double rate = calculateUserRate(user.getId());
        user.setRate(rate);
        userRepository.save(user);
        log.info("Private - обновлен рейтинг пользователя с id {}", user.getId());
    }

    private Double calculateRate(long eventId) {
        double countPositive = likeRepository.findCountByEventIdAndIsPositive(eventId, true)
                .orElse(0.0);
        double countTotal = likeRepository.findCountByEventId(eventId).orElse(0.0);
        if (countTotal != 0.0) {
            return new BigDecimal(countPositive / countTotal * 100)
                    .setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        }
        return null;
    }

    private Double calculateUserRate(long userId) {
        double countPositive = likeRepository.findCountByEventInitiatorIdAndIsPositive(userId, true)
                .orElse(0.0);
        double countTotal = likeRepository.findCountByEventInitiatorId(userId).orElse(0.0);
        if (countTotal != 0.0) {
            return new BigDecimal(countPositive / countTotal * 100)
                    .setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        }
        return null;
    }

    private void checkUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id = %d was not found.", userId));
        }
    }
}

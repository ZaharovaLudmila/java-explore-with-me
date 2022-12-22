package ru.practicum.ewmService.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmService.service.interfaces.EventService;
import ru.practicum.ewmService.service.interfaces.LikeService;
import ru.practicum.ewmService.service.interfaces.RequestService;
import ru.practicum.ewmService.model.dto.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
@Slf4j
@Validated
public class EventPrivateController {
    private final EventService eventService;
    private final RequestService requestService;
    private final LikeService likeService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> findEventsByInitiatorId(@PathVariable("userId") long userId,
                                                       @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                                       Integer from,
                                                       @Positive @RequestParam(name = "size", defaultValue = "10")
                                                       Integer size) {

        log.info("Private - получение событий по id инициатора = {}", userId);
        return eventService.userFindEventsByInitiatorId(userId, PageRequest.of(from / size, size));
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable("userId") long userId,
                                    @Validated @RequestBody UpdateEventRequest updateEventDto) {
        log.info("Private - Обновление события инициатором с id {}, id события {}", userId, updateEventDto.getEventId());
        return eventService.userUpdateEvent(userId, updateEventDto);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addEvent(@PathVariable("userId") long userId, @Validated @RequestBody NewEventDto newEventDto) {
        log.info("Private - Добавление нового события {} пользователем с id {}", newEventDto.getTitle(), userId);
        return eventService.userAddEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findInitiatorsEventById(@PathVariable("userId") long userId,
                                                @PathVariable("eventId") long eventId) {

        log.info("Private - получение события по id = {} инициатором с id = {}", eventId, userId);
        return eventService.userFindEventById(eventId, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable("userId") long userId,
                                    @PathVariable("eventId") long eventId) {
        log.info("Private - Отмена события с id  {} инициатором с id {}, ", userId, eventId);
        return eventService.userCancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventsRequests(@PathVariable("userId") long userId,
                                                           @PathVariable("eventId") long eventId) {

        log.info("Private - получение информации о запросах на участие в событии с id {} инициатором с id {}",
                eventId, userId);
        return requestService.userFindEventsRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmParticipationRequest(@PathVariable("userId") long userId,
                                                               @PathVariable("eventId") long eventId,
                                                               @PathVariable("reqId") long reqId) {
        log.info("Private - подтверждение зявки на участие c id = {} в событии с id = {} инициатором с id = {}",
                reqId, userId, eventId);
        return requestService.userConfirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectParticipationRequest(@PathVariable("userId") long userId,
                                                              @PathVariable("eventId") long eventId,
                                                              @PathVariable("reqId") long reqId) {
        log.info("Private - отклонение зявки на участие c id = {} в событии с id = {} инициатором с id = {}",
                reqId, userId, eventId);
        return requestService.userRejectRequest(userId, eventId, reqId);
    }

    @PutMapping("/{userId}/events/{eventId}/likes")
    public void addLike(@PathVariable("userId") long userId, @PathVariable("eventId") long eventId,
                        @RequestParam(name = "isPositive") Boolean isPositive) {
        log.info("Private - Добавление like/dislike = {} событию с id {} пользователем с id {}",
                isPositive, eventId, userId);
        likeService.userAddLike(userId, eventId, isPositive);
    }

    @DeleteMapping("/{userId}/events/{eventId}/likes")
    public void deleteLike(@PathVariable("userId") long userId, @PathVariable("eventId") long eventId) {
        log.info("Private - удаление like/dislike события с id {} пользователем с id {}", eventId, userId);
        likeService.userDeleteLike(userId, eventId);
    }
}

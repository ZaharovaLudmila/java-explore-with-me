package ru.practicum.ewmServer.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmServer.model.dto.AdminUpdateEventRequest;
import ru.practicum.ewmServer.model.dto.EventFullDto;
import ru.practicum.ewmServer.parametrs.EventsAdminFindParams;
import ru.practicum.ewmServer.service.interfaces.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin")
@Slf4j
@Validated
public class EventAdminController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<EventFullDto> searchEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                           @RequestParam(name = "states", required = false) List<String> states,
                                           @RequestParam(name = "categories", required = false) List<Long> categories,
                                           @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                           @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                           Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10")
                                           Integer size, HttpServletRequest request) {



        log.info("Admin - получение событий с фильтрацией по пользователям: {}, статусам: {} и категориям: {}",
                users, states, categories);
        return eventService.adminSearchEvents(new EventsAdminFindParams(users, states, categories, rangeStart,
         rangeEnd, from, size), request);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto editEvent(@Positive @PathVariable("eventId") long eventId,
                                  @RequestBody AdminUpdateEventRequest eventDto) {

        log.info("Admin - редактирование события по id: {}", eventId);
        return eventService.adminEditEvent(eventId, eventDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@Positive @PathVariable("eventId") long eventId) {

        log.info("Admin - публикация события с id: {}", eventId);
        return eventService.adminPublishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@Positive @PathVariable("eventId") long eventId) {

        log.info("Admin - отклонение события с id: {}", eventId);
        return eventService.adminRejectEvent(eventId);
    }
}

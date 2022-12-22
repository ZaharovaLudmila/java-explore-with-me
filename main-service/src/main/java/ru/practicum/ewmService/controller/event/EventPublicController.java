package ru.practicum.ewmService.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmService.model.dto.EventFullDto;
import ru.practicum.ewmService.model.dto.EventShortDto;
import ru.practicum.ewmService.parametrs.EventsPublicFindParams;
import ru.practicum.ewmService.service.interfaces.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/events")
@Slf4j
@Validated
public class EventPublicController {

    private final EventService eventService;

    @GetMapping()
    public List<EventShortDto> searchEvents(@RequestParam(name = "text", required = false) String text,
                                            @RequestParam(name = "categories", required = false) List<Long> categories,
                                            @RequestParam(name = "paid", defaultValue = "false") Boolean paid,
                                            @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                            @RequestParam(name = "onlyAvailable", defaultValue = "false")
                                            Boolean onlyAvailable,
                                            @RequestParam(name = "sort", required = false) String sort,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                            Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10")
                                            Integer size, HttpServletRequest request) {

        log.info("Public - получение событий с фильтрацией по описанию: {}", text);
        return eventService.publicSearchEvents(new EventsPublicFindParams(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, request.getRemoteAddr(), request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable("id") long id, HttpServletRequest request) {

        //вызов клиента для записи статистики
        log.info("Public - получение события по id: {}", id);
        return eventService.publicGetEventById(id, request.getRemoteAddr(), request.getRequestURI());
    }

    @GetMapping("/rate")
    public List<EventShortDto> getEventsRate(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0")
                                             Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10")
                                             Integer size) {

        //вызов клиента для записи статистики
        log.info("Public - получение всех опубликованных событий с сортировкой по рейтингу");
        return eventService.publicGetEventsRate(PageRequest.of(from / size, size, Sort.Direction.DESC, "rate"));
    }
}

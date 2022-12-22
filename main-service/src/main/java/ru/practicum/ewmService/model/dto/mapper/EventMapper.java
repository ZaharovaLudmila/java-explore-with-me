package ru.practicum.ewmService.model.dto.mapper;

import ru.practicum.ewmService.model.dto.EventFullDto;
import ru.practicum.ewmService.model.Event;
import ru.practicum.ewmService.model.dto.EventShortDto;
import ru.practicum.ewmService.parametrs.UtilDateFormatter;

public class EventMapper {

    public static EventShortDto toEventShortDto(Event event, int confirmedRequests) {
        return new EventShortDto(event.getAnnotation(), CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getEventDate()
                .format(UtilDateFormatter.getFormatter()), event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()), event.getPaid(), event.getTitle(), event.getViews(),
                event.getRate() != null ? event.getRate().toString() + " %" : "нет отзывов");
    }

    public static EventFullDto toEventFullDto(Event event, int confirmedRequests) {
        return new EventFullDto(event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getCreatedOn().format(UtilDateFormatter.getFormatter()),
                event.getDescription(),
                event.getEventDate().format(UtilDateFormatter.getFormatter()),
                event.getId(), UserMapper.toUserShortDto(event.getInitiator()), event.getLocation(), event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ?
                        event.getPublishedOn().format(UtilDateFormatter.getFormatter()) : null,
                event.getRequestModeration(), event.getState(), event.getTitle(), event.getViews(),
                event.getRate() != null ? event.getRate().toString() + " %" : "нет отзывов");
    }

}

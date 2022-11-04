package ru.practicum.ewm.model.dto.mapper;

import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.parametrs.UtilDateFormatter;

public class EventMapper {

    public static EventShortDto toEventShortDto(Event event, int confirmedRequests) {
        return new EventShortDto(event.getAnnotation(), CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getEventDate()
                .format(UtilDateFormatter.getFormatter()), event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()), event.getPaid(), event.getTitle(), event.getViews());
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
                event.getRequestModeration(), event.getState(), event.getTitle(), event.getViews());
    }

}

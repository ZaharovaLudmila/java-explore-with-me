package ru.practicum.ewmServer.model.dto.mapper;

import ru.practicum.ewmServer.model.Event;
import ru.practicum.ewmServer.model.dto.EventFullDto;
import ru.practicum.ewmServer.model.dto.EventShortDto;

import java.time.format.DateTimeFormatter;

public class EventMapper {

    public static EventShortDto toEventShortDto(Event event, int confirmedRequests) {
        return new EventShortDto(event.getAnnotation(), CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getEventDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()), event.getPaid(), event.getTitle(), event.getViews());
    }

    public static EventFullDto toEventFullDto(Event event, int confirmedRequests) {
        return new EventFullDto(event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getDescription(),
                event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getId(), UserMapper.toUserShortDto(event.getInitiator()), event.getLocation(), event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ?
                        event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null,
                event.getRequestModeration(), event.getState(), event.getTitle(), event.getViews());
    }

}

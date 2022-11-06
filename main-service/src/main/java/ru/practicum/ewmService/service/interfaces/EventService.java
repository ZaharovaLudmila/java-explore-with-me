package ru.practicum.ewmService.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewmService.model.dto.*;
import ru.practicum.ewmService.parametrs.EventsAdminFindParams;
import ru.practicum.ewmService.parametrs.EventsPublicFindParams;

import java.util.List;

public interface EventService {
    List<EventShortDto> publicSearchEvents(EventsPublicFindParams params);

    EventFullDto publicGetEventById(long eventId, String addr, String uri);

    List<EventShortDto> userFindEventsByInitiatorId(long userId, PageRequest of);

    EventFullDto userUpdateEvent(long userId, UpdateEventRequest updateEventDto);

    EventFullDto userAddEvent(long userId, NewEventDto newEventDto);

    EventFullDto userFindEventById(long eventId, long userId);

    EventFullDto userCancelEvent(long userId, long eventId);


    List<EventFullDto> adminSearchEvents(EventsAdminFindParams eventsAdminFindParams);

    EventFullDto adminEditEvent(long eventId, AdminUpdateEventRequest eventDto);

    EventFullDto adminPublishEvent(long eventId);

    EventFullDto adminRejectEvent(long eventId);
}


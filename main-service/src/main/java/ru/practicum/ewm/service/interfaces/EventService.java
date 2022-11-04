package ru.practicum.ewm.service.interfaces;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.parametrs.EventsAdminFindParams;
import ru.practicum.ewm.parametrs.EventsPublicFindParams;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    List<EventShortDto> publicSearchEvents(EventsPublicFindParams params);

    EventFullDto publicGetEventById(long eventId, HttpServletRequest request);

    List<EventShortDto> userFindEventsByInitiatorId(long userId, PageRequest of);

    EventFullDto userUpdateEvent(long userId, UpdateEventRequest updateEventDto);

    EventFullDto userAddEvent(long userId, NewEventDto newEventDto);

    EventFullDto userFindEventById(long eventId, long userId);

    EventFullDto userCancelEvent(long userId, long eventId);


    List<EventFullDto> adminSearchEvents(EventsAdminFindParams eventsAdminFindParams, HttpServletRequest request);

    EventFullDto adminEditEvent(long eventId, AdminUpdateEventRequest eventDto);

    EventFullDto adminPublishEvent(long eventId);

    EventFullDto adminRejectEvent(long eventId);
}

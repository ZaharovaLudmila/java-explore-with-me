package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.ConditionException;
import ru.practicum.ewm.model.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.dto.mapper.EventMapper;
import ru.practicum.ewm.repository.*;
import ru.practicum.ewm.exception.ActionForbiddenException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.parametrs.EventsAdminFindParams;
import ru.practicum.ewm.parametrs.EventsPublicFindParams;
import ru.practicum.ewm.parametrs.UtilDateFormatter;
import ru.practicum.ewm.service.interfaces.EventService;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final StatClientService statClient;
    private final DateTimeFormatter formatter = UtilDateFormatter.getFormatter();

    @Transactional
    @Override
    public List<EventShortDto> publicSearchEvents(EventsPublicFindParams params) {

        List<Event> events = eventRepository.findAllPublic(params.getText(), params.getCategories(), params.getPaid(),
                params.getRangeStart(), params.getRangeEnd(), params.getPage());
        events = statClient.getViews(events);
        if (params.getSort().equals(EventSort.VIEWS)) {
            events.sort(Comparator.comparingLong(Event::getViews).reversed());
        }
        statClient.saveStatistic(params.getAddr(), params.getUri());
        return events.stream()
                .map(event -> EventMapper.toEventShortDto(event,
                        requestRepository.getNumberOfConfirmRequest(event.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto publicGetEventById(long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id = %d was not found."));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ActionForbiddenException("Viewing an unpublished event is forbidden.");
        }
        event.setViews(statClient.getViewsById(event));
        statClient.saveStatistic(request.getRemoteAddr(), request.getRequestURI());
        return EventMapper.toEventFullDto(event, requestRepository.getNumberOfConfirmRequest(event.getId()));
    }

    @Transactional
    @Override
    public List<EventShortDto> userFindEventsByInitiatorId(long userId, PageRequest of) {
        checkUser(userId);
        List<Event> events = eventRepository.findAllByInitiatorId(userId);
        events = statClient.getViews(events);
        return events.stream().map(ev -> EventMapper.toEventShortDto(ev,
                        requestRepository.getNumberOfConfirmRequest(ev.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto userUpdateEvent(long userId, UpdateEventRequest updateEventDto) {
        Event event = eventRepository.findById(updateEventDto.getEventId()).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.",
                        updateEventDto.getEventId())));

        checkEventState(event);

        checkEventsInitiator(event, userId);

        LocalDateTime eventDate = null;
        LocalDateTime conditionDate = LocalDateTime.now().plusHours(2);
        if (updateEventDto.getEventDate() != null) {
            eventDate = LocalDateTime.parse(updateEventDto.getEventDate(), formatter);
            event.setEventDate(eventDate);
        }

        if (eventDate != null && eventDate.isBefore(conditionDate)) {
            throw new ConditionException(String.format(
                    "The date of the event cannot be earlier than in %s",
                    conditionDate.format(formatter)));
        }

        addEventData(event, updateEventDto.getAnnotation(), updateEventDto.getCategory(),
                updateEventDto.getDescription());

        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }

        if (updateEventDto.getParticipantLimit() > 0) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }

        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }

        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }

        event = eventRepository.save(event);
        event.setViews(statClient.getViewsById(event));
        return EventMapper.toEventFullDto(event,
                requestRepository.getNumberOfConfirmRequest(event.getId()));
    }

    private void addEventData(Event event, String annotation, Long categoryId, String description) {
        if (annotation != null) {
            event.setAnnotation(annotation);
        }

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                    new NotFoundException(String.format("Category with id = %d was not found.",
                            categoryId)));
            event.setCategory(category);
        }

        if (description != null) {
            event.setDescription(description);
        }
    }

    @Transactional
    @Override
    public EventFullDto userAddEvent(long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id = %d was not found.", userId)));

        Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(
                () -> new NotFoundException(String.format("Category with id = %d was not found.",
                        newEventDto.getCategory())));

        Location location = locationRepository.findLocationByLatAndLon(newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon()).orElse(locationRepository.save(
                new Location(0L, newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon())));

        LocalDateTime eventDate;
        LocalDateTime conditionDate = LocalDateTime.now().plusHours(2);
        if (newEventDto.getEventDate() != null) {
            eventDate = LocalDateTime.parse(newEventDto.getEventDate(), formatter);
        } else {
            throw new ConditionException("Event date cannot be blank.");
        }

        if (eventDate.isBefore(conditionDate)) {
            throw new ConditionException(String.format(
                    "The date of the event cannot be earlier than in %s",
                    conditionDate.format(formatter)));
        }
        Event event = eventRepository.save(
                new Event(0L, newEventDto.getAnnotation(), category, LocalDateTime.now(),
                        newEventDto.getDescription(), eventDate, user, location, newEventDto.getPaid(),
                        newEventDto.getParticipantLimit(), null, newEventDto.getRequestModeration(),
                        EventState.PENDING, newEventDto.getTitle(), 0));
        return EventMapper.toEventFullDto(event, 0);
    }

    @Transactional
    @Override
    public EventFullDto userFindEventById(long eventId, long userId) {
        checkUser(userId);
        Event event = eventRepository.findEventByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> new NotFoundException(String.format("Event with id = %d was not found.", eventId)));
        event.setViews(statClient.getViewsById(event));
        return EventMapper.toEventFullDto(event, requestRepository.getNumberOfConfirmRequest(event.getId()));
    }

    @Transactional
    @Override
    public EventFullDto userCancelEvent(long userId, long eventId) {
        checkUser(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));

        checkEventState(event);
        checkEventsInitiator(event, userId);
        event.setState(EventState.CANCELED);
        event.setViews(statClient.getViewsById(event));
        return EventMapper.toEventFullDto(event, requestRepository.getNumberOfConfirmRequest(event.getId()));
    }

    @Transactional
    @Override
    public List<EventFullDto> adminSearchEvents(EventsAdminFindParams params,
                                                HttpServletRequest request) {
        List<Event> events = eventRepository.findAllAdmin(params.getUsers(), params.getStates(), params.getCategories(),
                params.getRangeStart(), params.getRangeEnd(), params.getPage());
        events = statClient.getViews(events);
        return events.stream()
                .map(event -> EventMapper.toEventFullDto(event,
                        requestRepository.getNumberOfConfirmRequest(event.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto adminEditEvent(long eventId, AdminUpdateEventRequest eventDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));

        if (eventDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventDto.getEventDate(), formatter));
        }

        addEventData(event, eventDto.getAnnotation(), eventDto.getCategory(), eventDto.getDescription());

        if (eventDto.getLocation() != null) {
            Location location = locationRepository.findLocationByLatAndLon(eventDto.getLocation().getLat(),
                    eventDto.getLocation().getLon()).orElse(locationRepository.save(
                    new Location(0L, eventDto.getLocation().getLat(), eventDto.getLocation().getLon())));
            event.setLocation(location);
        }

        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }

        if (eventDto.getParticipantLimit() > 0) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }

        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }

        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }

        event = eventRepository.save(event);
        event.setViews(statClient.getViewsById(event));
        return EventMapper.toEventFullDto(event,
                requestRepository.getNumberOfConfirmRequest(event.getId()));
    }

    @Transactional
    @Override
    public EventFullDto adminPublishEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));
        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConditionException("You can only publish a pending event.");
        }

        LocalDateTime publishDate = LocalDateTime.now();
        if (event.getEventDate().isBefore(publishDate.plusHours(1))) {
            throw new ConditionException("The date of the event must be no earlier than 1 hour from the current date");
        }

        event.setPublishedOn(publishDate);
        event.setState(EventState.PUBLISHED);
        eventRepository.save(event);
        event.setViews(statClient.getViewsById(event));
        return EventMapper.toEventFullDto(event, requestRepository.getNumberOfConfirmRequest(eventId));
    }

    @Transactional
    @Override
    public EventFullDto adminRejectEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionException("Only unpublished events can be rejected");
        }

        event.setState(EventState.CANCELED);
        eventRepository.save(event);
        event.setViews(statClient.getViewsById(event));
        return EventMapper.toEventFullDto(event, requestRepository.getNumberOfConfirmRequest(eventId));
    }

    private void checkUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id = %d was not found.", userId));
        }
    }

    private void checkEventsInitiator(Event event, long userId) {
        if (event.getInitiator().getId() != userId) {
            throw new ActionForbiddenException(String.format("User id = %d is not the initiator of the event id = %d",
                    userId, event.getId()));
        }
    }

    private void checkEventState(Event event) {
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionException("Published events can not be changed.");
        }
    }
}

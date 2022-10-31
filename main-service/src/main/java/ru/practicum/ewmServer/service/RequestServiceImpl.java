package ru.practicum.ewmServer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmServer.exception.ConditionException;
import ru.practicum.ewmServer.exception.NotFoundException;
import ru.practicum.ewmServer.exception.ParticipationRequestException;
import ru.practicum.ewmServer.model.*;
import ru.practicum.ewmServer.model.dto.ParticipationRequestDto;
import ru.practicum.ewmServer.model.dto.mapper.RequestMapper;
import ru.practicum.ewmServer.repository.EventRepository;
import ru.practicum.ewmServer.repository.RequestRepository;
import ru.practicum.ewmServer.repository.UserRepository;
import ru.practicum.ewmServer.service.interfaces.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public List<ParticipationRequestDto> findRequestsByRequesterId(long userId) {
        checkUser(userId);
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ParticipationRequestDto addRequest(long userId, long eventId) {

        if (requestRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ParticipationRequestException(
                    String.format("Request for participation from the user id=%d in the event id=%d already exists.",
                            userId, eventId));
        }

        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id = %d was not found."));
        if (userId == event.getInitiator().getId()) {
            throw new ParticipationRequestException("Initiator of an event cannot add a request to its event");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ParticipationRequestException("You сan not participate in an unpublished event!");
        }

        if (event.getParticipantLimit() == 0 &&
                event.getParticipantLimit() == requestRepository.getNumberOfConfirmRequest(eventId)) {
            throw new ParticipationRequestException("The event has reached the participation request limit!");
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id = %d was not found.", userId)));
        return RequestMapper.toParticipationRequestDto(requestRepository.save(
                new Request(0L, LocalDateTime.now(), event, user,
                        event.getRequestModeration() ? StatusRequest.PENDING : StatusRequest.CONFIRMED)));

    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        checkUser(userId);
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(
                () -> new NotFoundException(String.format("Participation request with id=%d not found.",
                        requestId)));
        request.setStatus(StatusRequest.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Transactional
    @Override
    public List<ParticipationRequestDto> userFindEventsRequests(long userId, long eventId) {
        checkUser(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id = %d was not found.", eventId)));
        checkEventsInitiator(event, userId);

        return requestRepository.findAllByEventId(eventId).stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ParticipationRequestDto userConfirmRequest(long userId, long eventId, long reqId) {

        Request request = requestRepository.findUserEventsRequest(reqId, eventId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Participation request with id=%d for event id=%d created by user id=%d not found.",
                                reqId, eventId, userId)));
        Event event = request.getEvent();
        if (event.getParticipantLimit() == 0 && !event.getRequestModeration()) {
            return RequestMapper.toParticipationRequestDto(request);
        }

        if (event.getParticipantLimit() <= requestRepository.getNumberOfConfirmRequest(eventId)) {
            throw new ConditionException(String.format("Event id=%d participants limit has been reached",
                    eventId));
        }

        request.setStatus(StatusRequest.CONFIRMED);
        requestRepository.save(request);

        if (event.getParticipantLimit() <= requestRepository.getNumberOfConfirmRequest(eventId)) {
            List<Request> requestList = requestRepository.findNotConfirmedRequest(eventId);
            for (Request req : requestList) {
                req.setStatus(StatusRequest.REJECTED);
                requestRepository.save(req);
            }
        }
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Transactional
    @Override
    public ParticipationRequestDto userRejectRequest(long userId, long eventId, long reqId) {
        Request request = requestRepository.findUserEventsRequest(reqId, eventId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Participation request with id=%d for event id=%d created by user id=%d not found.",
                                reqId, eventId, userId)));
        request.setStatus(StatusRequest.REJECTED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    private void checkUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id = %d was not found.", userId));
        }
    }

    private void checkEventsInitiator(Event event, long userId) {
        if (event.getInitiator().getId() != userId) {
            throw new ConditionException(String.format("User id = %d is not the initiator of the event id = %d",
                    userId, event.getId()));
        }
    }

}

package ru.practicum.ewmServer.service.interfaces;

import ru.practicum.ewmServer.model.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> findRequestsByRequesterId(long userId);

    ParticipationRequestDto addRequest(long userId, long eventId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);

    List<ParticipationRequestDto> userFindEventsRequests(long userId, long eventId);

    ParticipationRequestDto userConfirmRequest(long userId, long eventId, long reqId);

    ParticipationRequestDto userRejectRequest(long userId, long eventId, long reqId);
}

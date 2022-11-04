package ru.practicum.ewm.model.dto.mapper;

import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.parametrs.UtilDateFormatter;


public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(request.getCreated()
                .format(UtilDateFormatter.getFormatter()), request.getEvent().getId(),
                request.getId(), request.getRequester().getId(), request.getStatus().toString());
    }
}

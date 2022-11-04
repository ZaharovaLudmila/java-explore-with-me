package ru.practicum.ewmService.model.dto.mapper;

import ru.practicum.ewmService.model.Request;
import ru.practicum.ewmService.model.dto.ParticipationRequestDto;
import ru.practicum.ewmService.parametrs.UtilDateFormatter;


public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(request.getCreated()
                .format(UtilDateFormatter.getFormatter()), request.getEvent().getId(),
                request.getId(), request.getRequester().getId(), request.getStatus().toString());
    }
}

package ru.practicum.ewm.service;

import ru.practicum.ewm.model.dto.EndpointHitDto;
import ru.practicum.ewm.model.dto.ViewsStats;

import java.util.List;

public interface StatService {
    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);

    List<ViewsStats> getStat(ParametersStat params);
}

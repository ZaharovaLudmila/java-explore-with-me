package ru.practicum.ewmStat.service;

import ru.practicum.ewmStat.model.dto.EndpointHitDto;
import ru.practicum.ewmStat.model.dto.ViewsStats;

import java.util.List;

public interface StatService {
    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);
    List<ViewsStats> getStat(ParametersStat params);
}

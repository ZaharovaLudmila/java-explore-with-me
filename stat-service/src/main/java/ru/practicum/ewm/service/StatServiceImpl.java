package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.dto.EndpointHitDto;
import ru.practicum.ewm.model.dto.StatsMapper;
import ru.practicum.ewm.model.dto.ViewsStats;
import ru.practicum.ewm.repository.StatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        return StatsMapper.toEndpointHitDto(
                statRepository.save(StatsMapper.toEndpointHit(endpointHitDto)));
    }

    @Override
    public List<ViewsStats> getStat(ParametersStat params) {
        if (params.getUnique()) {
            return statRepository.getStatsUnique(params.getStart(), params.getEnd(), params.getUris());
        } else {
            return statRepository.getStats(params.getStart(), params.getEnd(), params.getUris());
        }
    }
}

package ru.practicum.ewm.model.dto;

import ru.practicum.ewm.model.EndpointHit;


public class StatsMapper {
    public static EndpointHitDto toEndpointHitDto(EndpointHit hit) {
        return new EndpointHitDto(hit.getId(), hit.getApp(), hit.getUri(), hit.getIp(),
                hit.getTimestamp());
    }

    public static EndpointHit toEndpointHit(EndpointHitDto hitDto) {
        return new EndpointHit(hitDto.getId(), hitDto.getApp(), hitDto.getUri(), hitDto.getIp(),
                hitDto.getTimestamp());
    }
}

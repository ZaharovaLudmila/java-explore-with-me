package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.ViewsStats;
import ru.practicum.ewm.service.ParametersStat;
import ru.practicum.ewm.model.dto.EndpointHitDto;
import ru.practicum.ewm.service.StatService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public EndpointHitDto saveHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("stat-service: сохрание данных о просмотре ip: {}, app: {}, uri: {}", endpointHitDto.getIp(),
                endpointHitDto.getApp(), endpointHitDto.getUri());
        return statService.saveHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewsStats> getStat(@RequestParam("start") String start,
                                    @RequestParam("end") String end,
                                    @RequestParam(name = "uris", required = false) List<String> uris,
                                    @RequestParam(name = "unique", required = false, defaultValue = "false")
                                    Boolean unique) {
        log.info("stat-service: получение статистики за период с {} по {}, uris: {}", start, end, uris);
        return statService.getStat(new ParametersStat(start, end, uris, unique));
    }
}

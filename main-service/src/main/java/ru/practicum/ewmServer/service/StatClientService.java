package ru.practicum.ewmServer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewmServer.model.Event;
import ru.practicum.statClient.StatClient;
import ru.practicum.statClient.dto.EndpointHitDto;
import ru.practicum.statClient.dto.ViewsStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatClientService {
    private final StatClient statClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${app.name}")
    private String appName;

    public void saveStatistic(String ip, String uri) {
        EndpointHitDto endpointHitDto = new EndpointHitDto(appName, uri, ip, LocalDateTime.now());
        statClient.saveHit(endpointHitDto);
    }

    public List<Event> getViews(List<Event> events) {
        if (!events.isEmpty()) {
            Map<Event, String> eventsMap = events.stream()
                    .collect(Collectors.toMap(event -> event, event -> "/events/" + event.getId()));

            String start = returnDate(events.stream().min(Comparator.comparing(Event::getCreatedOn))
                    .get().getCreatedOn());
            String end = returnDate(LocalDateTime.now());

            ResponseEntity<Object> obj = statClient.getStats(start, end,
                    eventsMap.values().toArray(new String[0]), false);

            List<ViewsStats> statsList = (List<ViewsStats>) obj.getBody();
            Map<String, Integer> views = statsList != null && !statsList.isEmpty() ? statsList.stream().collect(
                    Collectors.toMap(ViewsStats::getUri, ViewsStats::getHits)) : null;
            if (views != null && !views.isEmpty()) {
                events.forEach(event -> event.setViews(views.get(eventsMap.get(event))));
            }
        }
        return events;
    }

    public int getViewsById(Event event) {
        String start = returnDate(event.getCreatedOn());
        String end = returnDate(LocalDateTime.now());
        ResponseEntity<Object> obj = statClient.getStats(start, end, new String[]{"/event/" + event.getId()}, false);
        List<ViewsStats> statsList = (List<ViewsStats>) obj.getBody();
        if (statsList != null && statsList.size() > 0) {
            return statsList.stream().findFirst().get().getHits();
        }
        return 0;
    }

    private String returnDate(LocalDateTime date) {
        return date.format(formatter);
    }
}

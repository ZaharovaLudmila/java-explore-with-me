package ru.practicum.ewmClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmClient.dto.EndpointHitDto;
import ru.practicum.ewmClient.dto.ViewsStats;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StatClient extends BaseClient {
    @Autowired
    public StatClient(@Value("${stat-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void saveHit(EndpointHitDto hitDto) {
        post("/hit", hitDto, new ParameterizedTypeReference<Object>() {});
    }

    public ResponseEntity<List<ViewsStats>> getStats(String start, String end, String[] uris, Boolean unique) {
        StringBuilder urisString = new StringBuilder();
        for (String uri : uris) {
            urisString.append("uris=").append(uri).append("&");
        }
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&" + urisString + "unique={unique}", parameters,
                new ParameterizedTypeReference<List<ViewsStats>>() {});
    }
}

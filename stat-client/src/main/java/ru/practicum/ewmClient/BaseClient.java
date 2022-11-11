package ru.practicum.ewmClient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T, U> ResponseEntity<U>  get(String path, @Nullable Map<String, Object> parameters,
                                            ParameterizedTypeReference<U> type) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null, type);
    }

    protected <T, U> ResponseEntity<U>  post(String path, T body, ParameterizedTypeReference<U> type) {
        return post(path, null, body, type);
    }

    protected <T, U> ResponseEntity<U> post(String path, @Nullable Map<String, Object> parameters, T body,
                                            ParameterizedTypeReference<U> type) {
        return makeAndSendRequest(HttpMethod.POST, path, parameters, body, type);
    }

    private <T, U> ResponseEntity<U>  makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String,
            Object> parameters, @Nullable T body, ParameterizedTypeReference<U> type) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<U> statResponse;
        try {
            if (parameters != null) {
                statResponse = rest.exchange(path, method, requestEntity, type, parameters);
            } else {
                statResponse = rest.exchange(path, method, requestEntity, type);
            }
        } catch (HttpStatusCodeException e) {
            return (ResponseEntity<U>) ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return statResponse;
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}

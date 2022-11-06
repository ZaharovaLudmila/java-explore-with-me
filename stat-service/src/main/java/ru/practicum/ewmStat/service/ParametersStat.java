package ru.practicum.ewmStat.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@ToString
public class ParametersStat {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private Boolean unique;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public ParametersStat(String start, String end, List<String> uris, Boolean unique) {
        this.start = start != null ? LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), FORMATTER) :
                    LocalDateTime.now().minusYears(100);
        this.end = end != null ? LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), FORMATTER) :
                LocalDateTime.now().plusYears(100);
        this.uris = uris;
        this.unique = unique;
    }
}

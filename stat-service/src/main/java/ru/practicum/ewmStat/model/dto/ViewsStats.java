package ru.practicum.ewmStat.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ViewsStats {
    private String app;
    private String uri;
    private long hits;
}

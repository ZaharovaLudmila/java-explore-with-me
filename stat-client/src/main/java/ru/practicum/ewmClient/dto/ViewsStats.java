package ru.practicum.ewmClient.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ViewsStats {
    private String app;
    private String uri;
    private int hits;
}

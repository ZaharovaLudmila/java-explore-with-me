package ru.practicum.ewmClient.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ViewsStats {
    private String uri;
    private int hits;
}

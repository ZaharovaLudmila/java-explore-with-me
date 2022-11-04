package ru.practicum.ewm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ViewsStats {
    private String uri;
    private int hits;
}
